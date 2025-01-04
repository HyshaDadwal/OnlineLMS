import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.user.dao.UserDAO;
import com.user.model.User;

class UserDAOTest {
	com.user.dao.UserDAO userDAO = new UserDAO();
	
	@Test
	void selectUser_testcase1()
	{
		User user = userDAO.selectUser(1);
		assertNotNull(user);
	}
	
	@Test
	void selectAllUsers_testcase2()
	{
		List<User> users = userDAO.selectAllUsers();
		assertTrue(users.size()>0);
	}
	
	@Test
	void deleteUser_testcase3()
	{
		Boolean status = userDAO.deleteUser(2);
		assertFalse(status); 
	}
}