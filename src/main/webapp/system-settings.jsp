<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>System Settings</title>
</head>
<body>
    <h1>Update System Settings</h1>
    <form action="SystemSettingsServlet" method="post">
        <label for="settings">Configuration Settings:</label><br>
        <textarea id="settings" name="settings" rows="10" cols="50"></textarea><br><br>
        
        <input type="submit" value="Update Settings">
    </form>
    <p>${message}</p>
</body>
</html>
