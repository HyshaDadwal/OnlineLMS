<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Assignment Grading</title>
</head>
<body>
    <h1>Grade Assignments</h1>
    <form action="GradeAssignmentServlet" method="post">
        <label for="studentSubmission">Student Submission:</label><br>
        <textarea id="studentSubmission" name="studentSubmission" rows="5" cols="40"></textarea><br><br>
        
        <label for="grade">Grade:</label>
        <input type="text" id="grade" name="grade"><br><br>
        
        <label for="feedback">Feedback:</label><br>
        <textarea id="feedback" name="feedback" rows="5" cols="40"></textarea><br><br>
        
        <input type="submit" value="Submit Grade">
    </form>
    <p>${message}</p>
</body>
</html>
