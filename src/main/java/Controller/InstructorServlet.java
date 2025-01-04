package Controller;

import com.user.dao.InstructorDAO;
import com.user.model.Instructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/instructors")
public class InstructorServlet extends HttpServlet {

    private InstructorDAO instructorDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        instructorDAO = new InstructorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listInstructors(request, response);
                break;
            case "delete":
                deleteInstructor(request, response);
                break;
            case "edit":
                editInstructor(request, response);
                break;
            default:
                listInstructors(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addInstructor(request, response);
        } else if ("update".equals(action)) {
            updateInstructor(request, response);
        }
    }

    private void listInstructors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("instructorList", instructorDAO.selectAllInstructors());
        request.getRequestDispatcher("/instructor_list.jsp").forward(request, response);
    }

    private void deleteInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        instructorDAO.deleteInstructor(instructorId);
        response.sendRedirect(request.getContextPath() + "/instructors?action=list");
    }

    private void editInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        Instructor instructor = instructorDAO.selectInstructor(instructorId);
        request.setAttribute("instructor", instructor);
        request.getRequestDispatcher("/instructor_edit.jsp").forward(request, response);
    }

    private void addInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String qualification = request.getParameter("qualification");
        String phoneNumber = request.getParameter("phoneNumber");

        Instructor instructor = new Instructor(0, fullName, qualification, phoneNumber);
        instructorDAO.insertInstructor(instructor);
        response.sendRedirect(request.getContextPath() + "/instructors?action=list");
    }
    
    private void updateInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        String fullName = request.getParameter("fullName");
        String qualification = request.getParameter("qualification");
        String phoneNumber = request.getParameter("phoneNumber");

        Instructor instructor = new Instructor(instructorId, fullName, qualification, phoneNumber);
        instructorDAO.updateInstructor(instructor);
        response.sendRedirect(request.getContextPath() + "/instructors?action=list");
    }

    @Override
    public void destroy() {
        instructorDAO = null;
    }
}
