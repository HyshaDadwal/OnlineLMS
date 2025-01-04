package Controller;

import com.user.dao.AdminDAO;
import com.user.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        adminDAO = new AdminDAO();
    }

    // Handles GET requests for admin dashboard or admin list
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listAdmins(request, response);
                break;
            case "delete":
                deleteAdmin(request, response);
                break;
            case "edit":
                editAdmin(request, response);
                break;
            default:
                listAdmins(request, response);
                break;
        }
    }

    // Handles POST requests for creating or updating an admin
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addAdmin(request, response);
        } else if ("update".equals(action)) {
            updateAdmin(request, response);
        }
    }

    private void listAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Admin> adminList = adminDAO.selectAllAdmins();
        request.setAttribute("adminList", adminList);
        request.getRequestDispatcher("/user-management.jsp").forward(request, response);
    }

    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        boolean isDeleted = adminDAO.deleteAdmin(adminId);

        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/admin?action=list");
        } else {
            request.setAttribute("message", "Error deleting admin");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void editAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        Admin admin = adminDAO.selectAdmin(adminId);

        if (admin != null) {
            request.setAttribute("admin", admin);
            request.getRequestDispatcher("/user-management.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin?action=list");
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String phonenumber = request.getParameter("phonenumber");

        Admin admin = new Admin(0,fullName, phonenumber);
        adminDAO.insertAdmin(admin);

        response.sendRedirect(request.getContextPath() + "/admin?action=list");
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        String fullName = request.getParameter("fullName");
        String phonenumber = request.getParameter("phonenumber");

        Admin admin = new Admin(adminId, fullName, phonenumber);
        boolean isUpdated = adminDAO.updateAdmin(admin);

        if (isUpdated) {
            response.sendRedirect(request.getContextPath() + "/admin?action=list");
        } else {
            request.setAttribute("message", "Error updating admin");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        adminDAO = null;
    }
}
