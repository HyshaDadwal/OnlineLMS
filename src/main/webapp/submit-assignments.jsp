<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Assignment</title>
</head>
<body>
    <h2>Submit Your Assignment</h2>
    <form action="submit_assignment_result.jsp" method="post" enctype="multipart/form-data">
        <label for="assignment">Select Assignment:</label>
        <select name="assignment" id="assignment">
            <option value="Assignment 1">Assignment 1</option>
            <option value="Assignment 2">Assignment 2</option>
            <option value="Assignment 3">Assignment 3</option>
        </select><br><br>
        <label for="file">Upload File:</label>
        <input type="file" name="file" id="file" required><br><br>
        <input type="submit" value="Submit Assignment">
    </form>
</body>
</html>