import com.user.dao.EnrollmentsDAO;
import com.user.model.Enrollments;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnrollmentsDAOTest {

    private EnrollmentsDAO enrollmentsDAO;

    @BeforeAll
    void setup() {
        enrollmentsDAO = new EnrollmentsDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(enrollmentsDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertEnrollment() {
        Enrollments enrollment = new Enrollments(0, 1, 1, null); // Assuming student_id=1 and course_id=1 exist
        enrollmentsDAO.insertEnrollment(enrollment);

        List<Enrollments> allEnrollments = enrollmentsDAO.selectAllEnrollments();
        assertTrue(allEnrollments.stream()
                .anyMatch(e -> e.getStudentId() == 1 && e.getCourseId() == 1),
                "Inserted enrollment should be present in the database");
    }

    @Test
    void testSelectEnrollment() {
        Enrollments enrollment = new Enrollments(0, 2, 2, null); // Assuming student_id=2 and course_id=2 exist
        enrollmentsDAO.insertEnrollment(enrollment);

        List<Enrollments> allEnrollments = enrollmentsDAO.selectAllEnrollments();
        Enrollments insertedEnrollment = allEnrollments.stream()
                .filter(e -> e.getStudentId() == 2 && e.getCourseId() == 2)
                .findFirst()
                .orElse(null);

        assertNotNull(insertedEnrollment, "Inserted enrollment should be retrievable");
        Enrollments retrievedEnrollment = enrollmentsDAO.selectEnrollment(insertedEnrollment.getEnrollmentId());
        assertNotNull(retrievedEnrollment, "Enrollment should be retrievable by ID");
        assertEquals(2, retrievedEnrollment.getStudentId(), "Student ID should match");
        assertEquals(2, retrievedEnrollment.getCourseId(), "Course ID should match");
    }

    @Test
    void testUpdateEnrollment() {
        Enrollments enrollment = new Enrollments(0, 3, 3, null); // Assuming student_id=3 and course_id=3 exist
        enrollmentsDAO.insertEnrollment(enrollment);

        List<Enrollments> allEnrollments = enrollmentsDAO.selectAllEnrollments();
        Enrollments insertedEnrollment = allEnrollments.stream()
                .filter(e -> e.getStudentId() == 3 && e.getCourseId() == 3)
                .findFirst()
                .orElse(null);

        assertNotNull(insertedEnrollment, "Inserted enrollment should be retrievable");

        insertedEnrollment.setStudentId(4); // Updating student_id to 4
        insertedEnrollment.setCourseId(4); // Updating course_id to 4

        boolean isUpdated = enrollmentsDAO.updateEnrollment(insertedEnrollment);
        assertTrue(isUpdated, "Enrollment should be updated");

        Enrollments updatedEnrollment = enrollmentsDAO.selectEnrollment(insertedEnrollment.getEnrollmentId());
        assertEquals(4, updatedEnrollment.getStudentId(), "Updated student ID should match");
        assertEquals(4, updatedEnrollment.getCourseId(), "Updated course ID should match");
    }

    @Test
    void testDeleteEnrollment() {
        Enrollments enrollment = new Enrollments(0, 5, 5, null); // Assuming student_id=5 and course_id=5 exist
        enrollmentsDAO.insertEnrollment(enrollment);

        List<Enrollments> allEnrollments = enrollmentsDAO.selectAllEnrollments();
        Enrollments insertedEnrollment = allEnrollments.stream()
                .filter(e -> e.getStudentId() == 5 && e.getCourseId() == 5)
                .findFirst()
                .orElse(null);

        assertNotNull(insertedEnrollment, "Inserted enrollment should be retrievable");

        boolean isDeleted = enrollmentsDAO.deleteEnrollment(insertedEnrollment.getEnrollmentId());
        assertTrue(isDeleted, "Enrollment should be deleted");

        Enrollments deletedEnrollment = enrollmentsDAO.selectEnrollment(insertedEnrollment.getEnrollmentId());
        assertNull(deletedEnrollment, "Deleted enrollment should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        List<Enrollments> allEnrollments = enrollmentsDAO.selectAllEnrollments();
        for (Enrollments enrollment : allEnrollments) {
            enrollmentsDAO.deleteEnrollment(enrollment.getEnrollmentId());
        }
    }
}
