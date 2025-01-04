import com.user.dao.StudentDAO;
import com.user.model.Student;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDAOTest {

    private StudentDAO studentDAO;

    @BeforeAll
    void setup() {
        studentDAO = new StudentDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(studentDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertStudent() {
        Student student = new Student(0, "Alice Johnson", "ENR001", Date.valueOf("2000-01-15"), "1234567890", 1);
        studentDAO.insertStudent(student);

        List<Student> allStudents = studentDAO.selectAllStudents();
        assertTrue(allStudents.stream()
                .anyMatch(s -> s.getFullName().equals("Alice Johnson") && s.getEnrollmentNumber().equals("ENR001")),
                "Inserted student should be present in the database");
    }

    @Test
    void testSelectStudent() {
        Student student = new Student(0, "Bob Smith", "ENR002", Date.valueOf("1999-05-25"), "9876543210", 2);
        studentDAO.insertStudent(student);

        List<Student> allStudents = studentDAO.selectAllStudents();
        int studentId = allStudents.stream()
                .filter(s -> s.getFullName().equals("Bob Smith") && s.getEnrollmentNumber().equals("ENR002"))
                .findFirst()
                .map(Student::getStudentId)
                .orElse(-1);

        assertNotEquals(-1, studentId, "Inserted student ID should be retrievable");

        Student retrievedStudent = studentDAO.selectStudent(studentId);
        assertNotNull(retrievedStudent, "Student should be retrievable by ID");
        assertEquals("Bob Smith", retrievedStudent.getFullName(), "Full name should match");
        assertEquals("ENR002", retrievedStudent.getEnrollmentNumber(), "Enrollment number should match");
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student(0, "Charlie Brown", "ENR003", Date.valueOf("1998-10-10"), "1112223333", 3);
        studentDAO.insertStudent(student);

        List<Student> allStudents = studentDAO.selectAllStudents();
        int studentId = allStudents.stream()
                .filter(s -> s.getFullName().equals("Charlie Brown") && s.getEnrollmentNumber().equals("ENR003"))
                .findFirst()
                .map(Student::getStudentId)
                .orElse(-1);

        assertNotEquals(-1, studentId, "Inserted student ID should be retrievable");

        Student updatedStudent = new Student(studentId, "Charlie Updated", "ENR999", Date.valueOf("1997-09-09"), "4445556666", 4);
        boolean isUpdated = studentDAO.updateStudent(updatedStudent);
        assertTrue(isUpdated, "Student should be updated");

        Student retrievedStudent = studentDAO.selectStudent(studentId);
        assertEquals("Charlie Updated", retrievedStudent.getFullName(), "Updated full name should match");
        assertEquals("ENR999", retrievedStudent.getEnrollmentNumber(), "Updated enrollment number should match");
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student(0, "Diana Prince", "ENR004", Date.valueOf("1996-03-15"), "7778889990", 5);
        studentDAO.insertStudent(student);

        List<Student> allStudents = studentDAO.selectAllStudents();
        int studentId = allStudents.stream()
                .filter(s -> s.getFullName().equals("Diana Prince") && s.getEnrollmentNumber().equals("ENR004"))
                .findFirst()
                .map(Student::getStudentId)
                .orElse(-1);

        assertNotEquals(-1, studentId, "Inserted student ID should be retrievable");

        boolean isDeleted = studentDAO.deleteStudent(studentId);
        assertTrue(isDeleted, "Student should be deleted");

        Student retrievedStudent = studentDAO.selectStudent(studentId);
        assertNull(retrievedStudent, "Deleted student should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        List<Student> allStudents = studentDAO.selectAllStudents();
        for (Student student : allStudents) {
            studentDAO.deleteStudent(student.getStudentId());
        }
    }
}
