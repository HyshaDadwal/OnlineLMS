import com.user.dao.SubmissionsDAO;
import com.user.model.Submissions;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubmissionsDAOTest {

    private SubmissionsDAO submissionsDAO;

    @BeforeAll
    void setup() {
        submissionsDAO = new SubmissionsDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(submissionsDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertSubmission() {
        Submissions submission = new Submissions(0, 101, 201, Timestamp.valueOf("2025-01-01 10:00:00"), "A");
        submissionsDAO.insertSubmission(submission);

        List<Submissions> allSubmissions = submissionsDAO.selectAllSubmissions();
        assertTrue(allSubmissions.stream()
                .anyMatch(s -> s.getAssignmentId() == 101 && s.getStudentId() == 201 && s.getGrade().equals("A")),
                "Inserted submission should be present in the database");
    }

    @Test
    void testSelectSubmission() {
        Submissions submission = new Submissions(0, 102, 202, Timestamp.valueOf("2025-01-02 11:00:00"), "B");
        submissionsDAO.insertSubmission(submission);

        List<Submissions> allSubmissions = submissionsDAO.selectAllSubmissions();
        int submissionId = allSubmissions.stream()
                .filter(s -> s.getAssignmentId() == 102 && s.getStudentId() == 202)
                .findFirst()
                .map(Submissions::getSubmissionId)
                .orElse(-1);

        assertNotEquals(-1, submissionId, "Inserted submission ID should be retrievable");

        Submissions retrievedSubmission = submissionsDAO.selectSubmission(submissionId);
        assertNotNull(retrievedSubmission, "Submission should be retrievable by ID");
        assertEquals(102, retrievedSubmission.getAssignmentId(), "Assignment ID should match");
        assertEquals(202, retrievedSubmission.getStudentId(), "Student ID should match");
    }

    @Test
    void testUpdateSubmission() {
        Submissions submission = new Submissions(0, 103, 203, Timestamp.valueOf("2025-01-03 12:00:00"), "C");
        submissionsDAO.insertSubmission(submission);

        List<Submissions> allSubmissions = submissionsDAO.selectAllSubmissions();
        int submissionId = allSubmissions.stream()
                .filter(s -> s.getAssignmentId() == 103 && s.getStudentId() == 203)
                .findFirst()
                .map(Submissions::getSubmissionId)
                .orElse(-1);

        assertNotEquals(-1, submissionId, "Inserted submission ID should be retrievable");

        Submissions updatedSubmission = new Submissions(submissionId, 104, 204, Timestamp.valueOf("2025-01-04 13:00:00"), "A+");
        boolean isUpdated = submissionsDAO.updateSubmission(updatedSubmission);
        assertTrue(isUpdated, "Submission should be updated");

        Submissions retrievedSubmission = submissionsDAO.selectSubmission(submissionId);
        assertEquals(104, retrievedSubmission.getAssignmentId(), "Updated assignment ID should match");
        assertEquals(204, retrievedSubmission.getStudentId(), "Updated student ID should match");
        assertEquals("A+", retrievedSubmission.getGrade(), "Updated grade should match");
    }

    @Test
    void testDeleteSubmission() {
        Submissions submission = new Submissions(0, 105, 205, Timestamp.valueOf("2025-01-05 14:00:00"), "B+");
        submissionsDAO.insertSubmission(submission);

        List<Submissions> allSubmissions = submissionsDAO.selectAllSubmissions();
        int submissionId = allSubmissions.stream()
                .filter(s -> s.getAssignmentId() == 105 && s.getStudentId() == 205)
                .findFirst()
                .map(Submissions::getSubmissionId)
                .orElse(-1);

        assertNotEquals(-1, submissionId, "Inserted submission ID should be retrievable");

        boolean isDeleted = submissionsDAO.deleteSubmission(submissionId);
        assertTrue(isDeleted, "Submission should be deleted");

        Submissions retrievedSubmission = submissionsDAO.selectSubmission(submissionId);
        assertNull(retrievedSubmission, "Deleted submission should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        List<Submissions> allSubmissions = submissionsDAO.selectAllSubmissions();
        for (Submissions submission : allSubmissions) {
            submissionsDAO.deleteSubmission(submission.getSubmissionId());
        }
    }
}
