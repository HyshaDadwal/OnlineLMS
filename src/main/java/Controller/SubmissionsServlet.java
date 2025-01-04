package Controller;

import com.user.dao.SubmissionsDAO;
import com.user.model.Enrollments;
import com.user.model.Submissions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/submissions")
public class SubmissionsServlet extends HttpServlet {

    private SubmissionsDAO submissionsDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        submissionsDAO = new SubmissionsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listSubmissions(request, response);
                break;
            case "delete":
                deleteSubmission(request, response);
                break;
            case "edit":
                editSubmission(request, response);
                break;
            default:
                listSubmissions(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addSubmission(request, response);
        } else if ("update".equals(action)) {
            updateSubmission(request, response);
        }
    }

    private void listSubmissions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("submissionsList", submissionsDAO.selectAllSubmissions());
        request.getRequestDispatcher("/submission_list.jsp").forward(request, response);
    }

    private void deleteSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        submissionsDAO.deleteSubmission(submissionId);
        response.sendRedirect(request.getContextPath() + "/submissions?action=list");
    }

    private void editSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        Submissions submission = submissionsDAO.selectSubmission(submissionId);
        request.setAttribute("submission", submission);
        request.getRequestDispatcher("/submission_edit.jsp").forward(request, response);
    }

    private void addSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String submissionDate = request.getParameter("submissionDate");
        String grade = request.getParameter("grade");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
		java.util.Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(submissionDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
        
        Submissions submission = new Submissions(0, assignmentId, studentId, timestamp, grade);
        submissionsDAO.insertSubmission(submission);
        response.sendRedirect(request.getContextPath() + "/submissions?action=list");
    }
    
    private void updateSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String submissionDate = request.getParameter("submissionDate");
        String grade = request.getParameter("grade");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
		java.util.Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(submissionDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		
        Submissions submission = new Submissions(submissionId, assignmentId, studentId, timestamp, grade);
        submissionsDAO.updateSubmission(submission);
        response.sendRedirect(request.getContextPath() + "/submissions?action=list");
    }

    @Override
    public void destroy() {
        submissionsDAO = null;
    }
}
