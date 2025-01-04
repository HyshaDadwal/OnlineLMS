import com.user.dao.CoursesDAO;
import com.user.model.Courses;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoursesDAOTest {

    private CoursesDAO coursesDAO;

    @BeforeAll
    void setup() {
        coursesDAO = new CoursesDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(coursesDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertCourse() {
        Courses course = new Courses(0, "Java Programming", "Learn the basics of Java", 1);
        coursesDAO.insertCourse(course);

        List<Courses> allCourses = coursesDAO.selectAllCourses();
        assertTrue(allCourses.stream()
                .anyMatch(c -> "Java Programming".equals(c.getCourseName()) && 1 == c.getCreatedBy()), 
                "Inserted course should be present in the database");
    }

    @Test
    void testSelectCourse() {
        Courses course = new Courses(0, "Python Programming", "Learn Python scripting", 2);
        coursesDAO.insertCourse(course);

        List<Courses> allCourses = coursesDAO.selectAllCourses();
        Courses insertedCourse = allCourses.stream()
                .filter(c -> "Python Programming".equals(c.getCourseName()))
                .findFirst()
                .orElse(null);

        assertNotNull(insertedCourse, "Inserted course should be retrievable");
        Courses retrievedCourse = coursesDAO.selectCourse(insertedCourse.getCourseId());
        assertNotNull(retrievedCourse, "Course should be retrievable by ID");
        assertEquals("Python Programming", retrievedCourse.getCourseName(), "Course name should match");
    }

    @Test
    void testUpdateCourse() {
        Courses course = new Courses(0, "Database Systems", "Introduction to SQL", 3);
        coursesDAO.insertCourse(course);

        List<Courses> allCourses = coursesDAO.selectAllCourses();
        Courses insertedCourse = allCourses.stream()
                .filter(c -> "Database Systems".equals(c.getCourseName()))
                .findFirst()
                .orElse(null);

        assertNotNull(insertedCourse, "Inserted course should be retrievable");
        insertedCourse.setCourseName("Advanced Database Systems");
        insertedCourse.setDescription("Advanced concepts in SQL");

        boolean isUpdated = coursesDAO.updateCourse(insertedCourse);
        assertTrue(isUpdated, "Course should be updated");

        Courses updatedCourse = coursesDAO.selectCourse(insertedCourse.getCourseId());
        assertEquals("Advanced Database Systems", updatedCourse.getCourseName(), "Updated course name should match");
        assertEquals("Advanced concepts in SQL", updatedCourse.getDescription(), "Updated description should match");
    }

    @Test
    void testDeleteCourse() {
        Courses course = new Courses(0, "Data Structures", "Learn about stacks and queues", 4);
        coursesDAO.insertCourse(course);

        List<Courses> allCourses = coursesDAO.selectAllCourses();
        Courses insertedCourse = allCourses.stream()
                .filter(c -> "Data Structures".equals(c.getCourseName()))
                .findFirst()
                .orElse(null);

        assertNotNull(insertedCourse, "Inserted course should be retrievable");

        boolean isDeleted = coursesDAO.deleteCourse(insertedCourse.getCourseId());
        assertTrue(isDeleted, "Course should be deleted");

        Courses deletedCourse = coursesDAO.selectCourse(insertedCourse.getCourseId());
        assertNull(deletedCourse, "Deleted course should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        List<Courses> allCourses = coursesDAO.selectAllCourses();
        for (Courses course : allCourses) {
            coursesDAO.deleteCourse(course.getCourseId());
        }
    }
}
