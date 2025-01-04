package Controller;

import com.user.dao.StudentDAO;
import com.user.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listStudents(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "edit":
                editStudent(request, response);
                break;
            default:
                listStudents(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addStudent(request, response);
        } else if ("update".equals(action)) {
            updateStudent(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("studentList", studentDAO.selectAllStudents());
        request.getRequestDispatcher("/student_list.jsp").forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        studentDAO.deleteStudent(studentId);
        response.sendRedirect(request.getContextPath() + "/students?action=list");
    }

    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Student student = studentDAO.selectStudent(studentId);
        request.setAttribute("student", student);
        request.getRequestDispatcher("/student_edit.jsp").forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String enrollmentNumber = request.getParameter("enrollmentNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String phoneNumber = request.getParameter("phoneNumber");
        int userId = Integer.parseInt(request.getParameter("userId"));

        Student student = new Student(0, fullName, enrollmentNumber, java.sql.Date.valueOf(dateOfBirth), phoneNumber, userId);
        studentDAO.insertStudent(student);
        response.sendRedirect(request.getContextPath() + "/students?action=list");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String fullName = request.getParameter("fullName");
        String enrollmentNumber = request.getParameter("enrollmentNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String phoneNumber = request.getParameter("phoneNumber");
        int userId = Integer.parseInt(request.getParameter("userId"));

        Student student = new Student(studentId, fullName, enrollmentNumber, java.sql.Date.valueOf(dateOfBirth), phoneNumber, userId);
        studentDAO.updateStudent(student);
        response.sendRedirect(request.getContextPath() + "/students?action=list");
    }

    @Override
    public void destroy() {
        studentDAO = null;
    }
}
