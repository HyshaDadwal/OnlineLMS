import com.user.dao.AssignmentsDAO;
import com.user.model.Assignments;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssignmentsDAOTest {

    private AssignmentsDAO assignmentsDAO;

    @BeforeAll
    void setup() {
        assignmentsDAO = new AssignmentsDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(assignmentsDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertAssignment() {
        Assignments assignment = new Assignments(0, 101, "Math Assignment", "Algebra problems", Date.valueOf("2025-01-15"));
        assignmentsDAO.insertAssignment(assignment);

        List<Assignments> allAssignments = assignmentsDAO.selectAllAssignments();
        assertTrue(allAssignments.stream()
                .anyMatch(a -> "Math Assignment".equals(a.getTitle()) && 101 == a.getCourseId()), "Inserted assignment should be present in the database");
    }

    @Test
    void testSelectAssignment() {
        Assignments assignment = new Assignments(0, 102, "Science Project", "Physics experiment", Date.valueOf("2025-02-20"));
        assignmentsDAO.insertAssignment(assignment);

        List<Assignments> allAssignments = assignmentsDAO.selectAllAssignments();
        Assignments insertedAssignment = allAssignments.stream()
                .filter(a -> "Science Project".equals(a.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(insertedAssignment, "Inserted assignment should be retrievable");
        Assignments retrievedAssignment = assignmentsDAO.selectAssignment(insertedAssignment.getAssignmentId());
        assertNotNull(retrievedAssignment, "Assignment should be retrievable by ID");
        assertEquals("Science Project", retrievedAssignment.getTitle(), "Title should match");
    }

    @Test
    void testUpdateAssignment() {
        Assignments assignment = new Assignments(0, 103, "History Assignment", "Write about WW2", Date.valueOf("2025-03-10"));
        assignmentsDAO.insertAssignment(assignment);

        List<Assignments> allAssignments = assignmentsDAO.selectAllAssignments();
        Assignments insertedAssignment = allAssignments.stream()
                .filter(a -> "History Assignment".equals(a.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(insertedAssignment, "Inserted assignment should be retrievable");
        insertedAssignment.setTitle("Updated History Assignment");
        insertedAssignment.setDescription("Updated description about WW2");

        boolean isUpdated = assignmentsDAO.updateAssignment(insertedAssignment);
        assertTrue(isUpdated, "Assignment should be updated");

        Assignments updatedAssignment = assignmentsDAO.selectAssignment(insertedAssignment.getAssignmentId());
        assertEquals("Updated History Assignment", updatedAssignment.getTitle(), "Updated title should match");
        assertEquals("Updated description about WW2", updatedAssignment.getDescription(), "Updated description should match");
    }

    @Test
    void testDeleteAssignment() {
        Assignments assignment = new Assignments(0, 104, "Geography Assignment", "Map studies", Date.valueOf("2025-04-01"));
        assignmentsDAO.insertAssignment(assignment);

        List<Assignments> allAssignments = assignmentsDAO.selectAllAssignments();
        Assignments insertedAssignment = allAssignments.stream()
                .filter(a -> "Geography Assignment".equals(a.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(insertedAssignment, "Inserted assignment should be retrievable");

        boolean isDeleted = assignmentsDAO.deleteAssignment(insertedAssignment.getAssignmentId());
        assertTrue(isDeleted, "Assignment should be deleted");

        Assignments deletedAssignment = assignmentsDAO.selectAssignment(insertedAssignment.getAssignmentId());
        assertNull(deletedAssignment, "Deleted assignment should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        List<Assignments> allAssignments = assignmentsDAO.selectAllAssignments();
        for (Assignments assignment : allAssignments) {
            assignmentsDAO.deleteAssignment(assignment.getAssignmentId());
        }
    }
}
