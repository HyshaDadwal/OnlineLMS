<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
</head>
<body>
    <h1>Manage Users</h1>
    <form action="UserManagementServlet" method="post">
        <label for="userName">Name:</label>
        <input type="text" id="userName" name="userName"><br><br>
        
        <label for="userEmail">Email:</label>
        <input type="email" id="userEmail" name="userEmail"><br><br>
        
        <label for="userRole">Role:</label>
        <select id="userRole" name="userRole">
            <option value="Admin">Admin</option>
            <option value="Instructor">Instructor</option>
            <option value="Student">Student</option>
        </select><br><br>
        
        <input type="submit" name="action" value="Create">
        <input type="submit" name="action" value="Update">
        <input type="submit" name="action" value="Delete">
    </form>
    <p>${message}</p>
</body>
</html>
