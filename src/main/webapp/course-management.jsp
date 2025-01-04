<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Management</title>
</head>
<body>
    <h1>Manage Courses</h1>
    <form action="CourseManagementServlet" method="post">
        <label for="courseTitle">Title:</label>
        <input type="text" id="courseTitle" name="courseTitle"><br><br>
        
        <label for="courseDescription">Description:</label><br>
        <textarea id="courseDescription" name="courseDescription" rows="5" cols="40"></textarea><br><br>
        
        <label for="courseSyllabus">Syllabus:</label><br>
        <textarea id="courseSyllabus" name="courseSyllabus" rows="5" cols="40"></textarea><br><br>
        
        <input type="submit" name="action" value="Create">
        <input type="submit" name="action" value="Update">
        <input type="submit" name="action" value="Delete">
    </form>
    <p>${message}</p>
</body>
</html>
