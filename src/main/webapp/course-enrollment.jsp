<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Enrollment</title>
</head>
<body>
    <h2>Enroll in a Course</h2>
    <form action="course_enrollment_result.jsp" method="post">
        <label for="course">Select Course:</label>
        <select name="course" id="course">
            <option value="Java Programming">Java Programming</option>
            <option value="Web Development">Web Development</option>
            <option value="Data Science">Data Science</option>
        </select><br><br>
        <input type="submit" value="Enroll">
    </form>
</body>
</html>