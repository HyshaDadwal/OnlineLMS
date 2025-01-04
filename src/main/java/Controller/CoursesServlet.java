package Controller;

import com.user.dao.CoursesDAO;
import com.user.model.Courses;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {

    private CoursesDAO coursesDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        coursesDAO = new CoursesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCourses(request, response);
                break;
            case "delete":
                deleteCourse(request, response);
                break;
            case "edit":
                editCourse(request, response);
                break;
            default:
                listCourses(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addCourse(request, response);
        } else if ("update".equals(action)) {
            updateCourse(request, response);
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("coursesList", coursesDAO.selectAllCourses());
        request.getRequestDispatcher("/course_list.jsp").forward(request, response);
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        coursesDAO.deleteCourse(courseId);
        response.sendRedirect(request.getContextPath() + "/courses?action=list");
    }
    
    private void editCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        
        Courses course = coursesDAO.selectCourse(courseId);
        request.setAttribute("course", course);
        request.getRequestDispatcher("/course_edit.jsp").forward(request, response);
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 int courseId = Integer.parseInt(request.getParameter("courseId"));
         String courseName = request.getParameter("courseName");
         String description = request.getParameter("description");
         int createdBy = Integer.parseInt(request.getParameter("createdBy"));

        Courses course = new Courses(courseId, courseName, description, createdBy);
        coursesDAO.insertCourse(course);
        response.sendRedirect(request.getContextPath() + "/courses?action=list");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String courseName = request.getParameter("courseName");
        String description = request.getParameter("description");
        int createdBy = Integer.parseInt(request.getParameter("createdBy"));

        Courses course = new Courses(courseId, courseName, description, createdBy);
        coursesDAO.updateCourse(course);
        response.sendRedirect(request.getContextPath() + "/courses?action=list");
    }

    @Override
    public void destroy() {
        coursesDAO = null;
    }
}
