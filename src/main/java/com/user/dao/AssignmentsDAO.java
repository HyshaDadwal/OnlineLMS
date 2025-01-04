package com.user.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.Assignment;

import com.user.model.Assignments;

public class AssignmentsDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/onlinelmsdb";
    private String jdbcUserName = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_ASSIGNMENT_SQL = "INSERT INTO Assignments (course_id, title, description, due_date) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ASSIGNMENT_BY_ID = "SELECT * FROM Assignments WHERE assignment_id = ?;";
    private static final String SELECT_ALL_ASSIGNMENTS = "SELECT * FROM Assignments;";
    private static final String DELETE_ASSIGNMENT_SQL = "DELETE FROM Assignments WHERE assignment_id = ?;";
    private static final String UPDATE_ASSIGNMENT_SQL = "UPDATE Assignments SET course_id = ?, title = ?, description = ?, due_date = ? WHERE assignment_id = ?;";

    public AssignmentsDAO() {
    }

    // Get database connection
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Insert a new assignment
    public void insertAssignment(Assignments assignment) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ASSIGNMENT_SQL)) {
            preparedStatement.setInt(1, assignment.getCourseId());
            preparedStatement.setString(2, assignment.getTitle());
            preparedStatement.setString(3, assignment.getDescription());
            preparedStatement.setDate(4, assignment.getDueDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Select an assignment by ID
    public Assignments selectAssignment(int id) {
        Assignments assignment = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ASSIGNMENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assignment = new Assignments(
                        resultSet.getInt("assignment_id"),
                        resultSet.getInt("course_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("due_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignment;
    }

    // Select all assignments
    public List<Assignments> selectAllAssignments() {
        List<Assignments> assignments = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ASSIGNMENTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                assignments.add(new Assignments(
                        resultSet.getInt("assignment_id"),
                        resultSet.getInt("course_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("due_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    // Delete an assignment by ID
    public boolean deleteAssignment(int id) {
        boolean status = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ASSIGNMENT_SQL)) {
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // Update an assignment
    public boolean updateAssignment(Assignments assignment) {
        boolean status = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ASSIGNMENT_SQL)) {
            preparedStatement.setInt(1, assignment.getCourseId());
            preparedStatement.setString(2, assignment.getTitle());
            preparedStatement.setString(3, assignment.getDescription());
            preparedStatement.setDate(4, assignment.getDueDate());
            preparedStatement.setInt(5, assignment.getAssignmentId());
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static void main(String[] args) {
        AssignmentsDAO dao = new AssignmentsDAO();
        if (dao.getConnection() != null) {
            System.out.println("Successfully connected to the database!!");
        } else {
            System.out.println("Problem in database connection!!");
        }
    }

	public boolean updateAssignment(Assignment assignment) {
		// TODO Auto-generated method stub
		return false;
	}
}
