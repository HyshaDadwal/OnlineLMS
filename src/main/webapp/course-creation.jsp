<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Creation</title>
</head>
<body>
    <h1>Create a New Course</h1>
    <form action="CreateCourseServlet" method="post">
        <label for="courseContent">Course Content:</label><br>
        <textarea id="courseContent" name="courseContent" rows="5" cols="40"></textarea><br><br>
        
        <label for="quizzes">Quizzes:</label><br>
        <textarea id="quizzes" name="quizzes" rows="5" cols="40"></textarea><br><br>
        
        <label for="assignments">Assignments:</label><br>
        <textarea id="assignments" name="assignments" rows="5" cols="40"></textarea><br><br>
        
        <input type="submit" value="Create Course">
    </form>
    <p>${message}</p>
</body>
</html>
