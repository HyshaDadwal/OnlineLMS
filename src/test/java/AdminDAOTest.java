import com.user.dao.AdminDAO;
import com.user.model.Admin;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminDAOTest {

    private AdminDAO adminDAO;

    @BeforeAll
    void setup() {
        adminDAO = new AdminDAO();
    }

    @Test
    void testConnection() {
        assertNotNull(adminDAO.getConnection(), "Database connection should not be null");
    }

    @Test
    void testInsertAdmin() {
        Admin admin = new Admin(1, "John Doe", "1234567890");
        adminDAO.insertAdmin(admin);
        Admin retrievedAdmin = adminDAO.selectAdmin(1);

        assertNotNull(retrievedAdmin, "Inserted admin should be retrievable");
        assertEquals(admin.getAdminId(), retrievedAdmin.getAdminId(), "Admin ID should match");
        assertEquals(admin.getFullName(), retrievedAdmin.getFullName(), "Admin full name should match");
        assertEquals(admin.getPhoneNumber(), retrievedAdmin.getPhoneNumber(), "Admin phone number should match");
    }

    @Test
    void testSelectAdmin() {
        Admin admin = new Admin(2, "Jane Smith", "0987654321");
        adminDAO.insertAdmin(admin);

        Admin retrievedAdmin = adminDAO.selectAdmin(2);
        assertNotNull(retrievedAdmin, "Admin should be retrievable by ID");
        assertEquals("Jane Smith", retrievedAdmin.getFullName(), "Admin full name should match");
    }

    @Test
    void testSelectAllAdmins() {
        List<Admin> admins = adminDAO.selectAllAdmins();
        assertNotNull(admins, "Admin list should not be null");
        assertTrue(admins.size() > 0, "Admin list should contain at least one admin");
    }

    @Test
    void testUpdateAdmin() {
        Admin admin = new Admin(3, "Mark Twain", "1112223333");
        adminDAO.insertAdmin(admin);

        admin.setFullName("Mark Twain Updated");
        admin.setPhoneNumber("9998887777");

        boolean isUpdated = adminDAO.updateAdmin(admin);
        assertTrue(isUpdated, "Admin should be updated");

        Admin updatedAdmin = adminDAO.selectAdmin(3);
        assertEquals("Mark Twain Updated", updatedAdmin.getFullName(), "Updated full name should match");
        assertEquals("9998887777", updatedAdmin.getPhoneNumber(), "Updated phone number should match");
    }

    @Test
    void testDeleteAdmin() {
        Admin admin = new Admin(4, "To Be Deleted", "4445556666");
        adminDAO.insertAdmin(admin);

        boolean isDeleted = adminDAO.deleteAdmin(4);
        assertTrue(isDeleted, "Admin should be deleted");

        Admin deletedAdmin = adminDAO.selectAdmin(4);
        assertNull(deletedAdmin, "Deleted admin should not be retrievable");
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        List<Admin> admins = adminDAO.selectAllAdmins();
        for (Admin admin : admins) {
            adminDAO.deleteAdmin(admin.getAdminId());
        }
    }
}
