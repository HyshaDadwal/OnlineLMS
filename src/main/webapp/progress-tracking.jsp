<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Track Course Progress</title>
</head>
<body>
    <h2>Track Your Course Progress</h2>
    <form action="progress_report.jsp" method="post">
        <label for="course">Select Course:</label>
        <select name="course" id="course">
            <option value="Java Programming">Java Programming</option>
            <option value="Web Development">Web Development</option>
            <option value="Data Science">Data Science</option>
        </select><br><br>
        <input type="submit" value="Get Progress Report">
    </form>

    <h3>Your Progress Report:</h3>
    <p>Course: <strong>Java Programming</strong></p>
    <p>Completed Lessons: 3 out of 5</p>
    <p>Assignments Submitted: 2 out of 3</p>
</body>
</html>