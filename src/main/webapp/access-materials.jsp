<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Access Course Materials</title>
</head>
<body>
    <h2>Course Materials</h2>
    <form action="access_materials.jsp" method="post">
        <label for="course">Select Course to View Materials:</label>
        <select name="course" id="course">
            <option value="Java Programming">Java Programming</option>
            <option value="Web Development">Web Development</option>
            <option value="Data Science">Data Science</option>
        </select><br><br>
        <input type="submit" value="Access Materials">
    </form>
    <h3>Materials for Selected Course:</h3>
    <ul>
        <li><a href="#">Lesson 1 PDF</a></li>
        <li><a href="#">Lesson 2 PDF</a></li>
        <li><a href="#">Lesson 3 PDF</a></li>
    </ul>
</body>
</html>