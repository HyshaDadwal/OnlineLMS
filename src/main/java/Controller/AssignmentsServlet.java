package Controller;

import com.user.dao.AssignmentsDAO;
import com.user.model.Assignments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.eclipse.jdt.internal.compiler.ast.Assignment;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/assignments")
public class AssignmentsServlet extends HttpServlet {

    private AssignmentsDAO assignmentsDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        assignmentsDAO = new AssignmentsDAO();
    }

    // Handles GET requests for displaying assignments or specific assignment
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listAssignments(request, response);
                break;
            case "delete":
                deleteAssignment(request, response);
                break;
            case "edit":
                editAssignment(request, response);
                break;
            case "view":
                viewAssignment(request, response);
                break;
            default:
                listAssignments(request, response);
                break;
        }
    }

    // Handles POST requests for creating or updating an assignment
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            addAssignment(request, response);
        } else if ("update".equals(action)) {
            updateAssignment(request, response);
        }
    }

    private void listAssignments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Assignments> assignmentList = assignmentsDAO.selectAllAssignments();
        request.setAttribute("assignmentList", assignmentList);
        request.getRequestDispatcher("/assignments_list.jsp").forward(request, response);
    }

    private void deleteAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        boolean isDeleted = assignmentsDAO.deleteAssignment(assignmentId);

        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/assignments?action=list");
        } else {
            request.setAttribute("message", "Error deleting assignment");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void editAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        Assignments assignment = assignmentsDAO.selectAssignment(assignmentId);

        if (assignment != null) {
            request.setAttribute("assignment", assignment);
            request.getRequestDispatcher("/assignment_edit.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/assignments?action=list");
        }
    }

    private void viewAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        Assignments assignment = assignmentsDAO.selectAssignment(assignmentId);

        if (assignment != null) {
            request.setAttribute("assignment", assignment);
            request.getRequestDispatcher("/assignment_view.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/assignments?action=list");
        }
    }
    
    private void addAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        int  courseId = Integer.parseInt(request.getParameter("courseId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	java.util.Date date = dateFormat.parse(dueDate);
        	response.getWriter().println("Parsed Date: "+ date);
        } catch(ParseException e) {
        	response.getWriter().println("Invalid date format: "+e.getMessage());
        }

        Assignment assignment = new Assignment(assignmentId, courseId, title, description, date);
        assignmentsDAO.insertAssignment(assignment);

        response.sendRedirect(request.getContextPath() + "/assignments?action=list");
    }

    private void updateAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        String courseId = request.getParameter("courseId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	java.util.Date date = dateFormat.parse(dueDate);
        	response.getWriter().println("Parsed Date: "+date);
        } catch(ParseException e) {
        	response.getWriter().println("Invalid date format: "+e.getMessage());
        }
        
		Assignment assignment = new Assignment(assignmentId, courseId, title, description, date);
        boolean isUpdated = assignmentsDAO.updateAssignment(assignment);

        if (isUpdated) {
            response.sendRedirect(request.getContextPath() + "/assignments?action=list");
        } else {
            request.setAttribute("message", "Error updating assignment");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        assignmentsDAO = null;
    }
}
