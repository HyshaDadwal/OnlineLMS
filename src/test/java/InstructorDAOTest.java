import com.user.dao.InstructorDAO;
import com.user.model.Instructor;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InstructorDAOTest {

    private InstructorDAO instructorDAO;

    @BeforeAll
    void setup() {
        instructorDAO = new InstructorDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(instructorDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertInstructor() {
        Instructor instructor = new Instructor(1, "John Doe", "PhD in Computer Science", "1234567890");
        instructorDAO.insertInstructor(instructor);

        List<Instructor> allInstructors = instructorDAO.selectAllInstructors();
        assertTrue(allInstructors.stream()
                .anyMatch(i -> i.getInstructorId() == 1 && i.getFullName().equals("John Doe")),
                "Inserted instructor should be present in the database");
    }

    @Test
    void testSelectInstructor() {
        Instructor instructor = new Instructor(2, "Jane Smith", "Masters in Mathematics", "9876543210");
        instructorDAO.insertInstructor(instructor);

        Instructor retrievedInstructor = instructorDAO.selectInstructor(2);
        assertNotNull(retrievedInstructor, "Instructor should be retrievable by ID");
        assertEquals("Jane Smith", retrievedInstructor.getFullName(), "Full name should match");
        assertEquals("Masters in Mathematics", retrievedInstructor.getQualification(), "Qualification should match");
        assertEquals("9876543210", retrievedInstructor.getPhoneNumber(), "Phone number should match");
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor(3, "Mark Taylor", "Bachelors in Physics", "1112223333");
        instructorDAO.insertInstructor(instructor);

        Instructor insertedInstructor = instructorDAO.selectInstructor(3);
        assertNotNull(insertedInstructor, "Inserted instructor should be retrievable");

        insertedInstructor.setFullName("Mark Updated");
        insertedInstructor.setQualification("PhD in Physics");
        insertedInstructor.setPhoneNumber("4445556666");

        boolean isUpdated = instructorDAO.updateInstructor(insertedInstructor);
        assertTrue(isUpdated, "Instructor should be updated");

        Instructor updatedInstructor = instructorDAO.selectInstructor(3);
        assertEquals("Mark Updated", updatedInstructor.getFullName(), "Updated full name should match");
        assertEquals("PhD in Physics", updatedInstructor.getQualification(), "Updated qualification should match");
        assertEquals("4445556666", updatedInstructor.getPhoneNumber(), "Updated phone number should match");
    }

    @Test
    void testDeleteInstructor() {
        Instructor instructor = new Instructor(4, "Alice Brown", "Masters in Chemistry", "7778889990");
        instructorDAO.insertInstructor(instructor);

        Instructor insertedInstructor = instructorDAO.selectInstructor(4);
        assertNotNull(insertedInstructor, "Inserted instructor should be retrievable");

        boolean isDeleted = instructorDAO.deleteInstructor(4);
        assertTrue(isDeleted, "Instructor should be deleted");

        Instructor deletedInstructor = instructorDAO.selectInstructor(4);
        assertNull(deletedInstructor, "Deleted instructor should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        List<Instructor> allInstructors = instructorDAO.selectAllInstructors();
        for (Instructor instructor : allInstructors) {
            instructorDAO.deleteInstructor(instructor.getInstructorId());
        }
    }
}
