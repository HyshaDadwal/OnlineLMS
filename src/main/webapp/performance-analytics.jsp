<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Performance Analytics</title>
</head>
<body>
    <h1>Analyze Performance</h1>
    <form action="PerformanceAnalyticsServlet" method="post">
        <label for="coursePerformance">Course Performance Data:</label><br>
        <textarea id="coursePerformance" name="coursePerformance" rows="5" cols="40"></textarea><br><br>
        
        <label for="userPerformance">User Performance Data:</label><br>
        <textarea id="userPerformance" name="userPerformance" rows="5" cols="40"></textarea><br><br>
        
        <input type="submit" value="Generate Report">
    </form>
    <h2>Performance Report:</h2>
    <pre>${analyticsReport}</pre>
</body>
</html>
