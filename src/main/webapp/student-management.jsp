<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Management</title>
</head>
<body>
    <h1>Manage Students</h1>
    <form action="ManageStudentsServlet" method="post">
        <label for="studentList">Student List:</label><br>
        <textarea id="studentList" name="studentList" rows="5" cols="40"></textarea><br><br>
        
        <input type="submit" value="Generate Report">
    </form>
    <h2>Student Performance Report:</h2>
    <pre>${performanceReport}</pre>
</body>
</html>
