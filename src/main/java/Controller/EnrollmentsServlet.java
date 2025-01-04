package Controller;

import com.user.dao.EnrollmentsDAO;
import com.user.model.Enrollments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/enrollments")
public class EnrollmentsServlet extends HttpServlet {

    private EnrollmentsDAO enrollmentsDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        enrollmentsDAO = new EnrollmentsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listEnrollments(request, response);
                break;
            case "delete":
                deleteEnrollment(request, response);
                break;
            case "edit":
                editEnrollment(request, response);
                break;
            default:
                listEnrollments(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addEnrollment(request, response);
        } else if ("update".equals(action)) {
            updateEnrollment(request, response);
        }
    }

    private void listEnrollments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("enrollmentsList", enrollmentsDAO.selectAllEnrollments());
        request.getRequestDispatcher("/enrollment_list.jsp").forward(request, response);
    }

    private void deleteEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        enrollmentsDAO.deleteEnrollment(enrollmentId);
        response.sendRedirect(request.getContextPath() + "/enrollments?action=list");
    }

    private void editEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        Enrollments enrollment = enrollmentsDAO.selectEnrollment(enrollmentId);
        request.setAttribute("enrollment", enrollment);
        request.getRequestDispatcher("/enrollment_edit.jsp").forward(request, response);
    }
    
    private void addEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String enrolledAt = request.getParameter("enrolledAt");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
		java.util.Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(enrolledAt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		
        Enrollments enrollment = new Enrollments(enrollmentId, studentId, courseId, timestamp);
        enrollmentsDAO.insertEnrollment(enrollment);
        response.sendRedirect(request.getContextPath() + "/enrollments?action=list");
    }

    private void updateEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String enrolledAt = request.getParameter("enrolledAt");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
		java.util.Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(enrolledAt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		
        Enrollments enrollment = new Enrollments(enrollmentId, studentId, courseId, timestamp);
        enrollmentsDAO.updateEnrollment(enrollment);
        response.sendRedirect(request.getContextPath() + "/enrollments?action=list");
    }

    @Override
    public void destroy() {
        enrollmentsDAO = null;
    }
}
