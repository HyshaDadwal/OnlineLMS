<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .section {
            border: 1px solid #ddd;
            margin: 10px 0;
            padding: 15px;
            border-radius: 5px;
        }
        .section h2 {
            margin-top: 0;
        }
    </style>
</head>
<body>
    <h1>Dashboard</h1>
    <p>Welcome, ${userRole}!</p>
    
    <!-- Admin Functionalities -->
    <c:if test="${userRole == 'Admin'}">
        <div class="section">
            <h2>User Management</h2>
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
        </div>

        <div class="section">
            <h2>Course Management</h2>
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
        </div>

        <div class="section">
            <h2>Performance Analytics</h2>
            <form action="PerformanceAnalyticsServlet" method="post">
                <label for="coursePerformance">Course Performance Data:</label><br>
                <textarea id="coursePerformance" name="coursePerformance" rows="5" cols="40"></textarea><br><br>
                
                <label for="userPerformance">User Performance Data:</label><br>
                <textarea id="userPerformance" name="userPerformance" rows="5" cols="40"></textarea><br><br>
                
                <input type="submit" value="Generate Report">
            </form>
        </div>

        <div class="section">
            <h2>System Settings</h2>
            <form action="SystemSettingsServlet" method="post">
                <label for="settings">Configuration Settings:</label><br>
                <textarea id="settings" name="settings" rows="10" cols="50"></textarea><br><br>
                
                <input type="submit" value="Update Settings">
            </form>
        </div>
    </c:if>
    
    <!-- Instructor Functionalities -->
    <c:if test="${userRole == 'Instructor'}">
        <div class="section">
            <h2>Course Creation</h2>
            <form action="CreateCourseServlet" method="post">
                <label for="courseContent">Course Content:</label><br>
                <textarea id="courseContent" name="courseContent" rows="5" cols="40"></textarea><br><br>
                
                <label for="quizzes">Quizzes:</label><br>
                <textarea id="quizzes" name="quizzes" rows="5" cols="40"></textarea><br><br>
                
                <label for="assignments">Assignments:</label><br>
                <textarea id="assignments" name="assignments" rows="5" cols="40"></textarea><br><br>
                
                <input type="submit" value="Create Course">
            </form>
        </div>

        <div class="section">
            <h2>Assignment Grading</h2>
            <form action="GradeAssignmentServlet" method="post">
                <label for="studentSubmission">Student Submission:</label><br>
                <textarea id="studentSubmission" name="studentSubmission" rows="5" cols="40"></textarea><br><br>
                
                <label for="grade">Grade:</label>
                <input type="text" id="grade" name="grade"><br><br>
                
                <label for="feedback">Feedback:</label><br>
                <textarea id="feedback" name="feedback" rows="5" cols="40"></textarea><br><br>
                
                <input type="submit" value="Submit Grade">
            </form>
        </div>

        <div class="section">
            <h2>Student Management</h2>
            <form action="ManageStudentsServlet" method="post">
                <label for="studentList">Student List:</label><br>
                <textarea id="studentList" name="studentList" rows="5" cols="40"></textarea><br><br>
                
                <input type="submit" value="Generate Report">
            </form>
        </div>
    </c:if>
    
    <!-- Student Functionalities -->
    <c:if test="${userRole == 'Student'}">
        <div class="section">
            <h2>Course Enrollment</h2>
            <form action="EnrollCourseServlet" method="post">
                <label for="courseSelection">Select Course:</label><br>
                <select id="courseSelection" name="courseSelection">
                    <!-- Populate courses dynamically -->
                    <option value="Course1">Course 1</option>
                    <option value="Course2">Course 2</option>
                </select><br><br>
                
                <input type="submit" value="Enroll">
            </form>
        </div>

        <div class="section">
            <h2>Access Materials</h2>
            <p>View and download your course materials from the course dashboard.</p>
        </div>

        <div class="section">
            <h2>Submit Assignments</h2>
            <form action="SubmitAssignmentServlet" method="post">
                <label for="assignment">Upload Assignment:</label>
                <input type="file" id="assignment" name="assignment"><br><br>
                
                <input type="submit" value="Submit">
            </form>
        </div>

        <div class="section">
            <h2>Progress Tracking</h2>
            <p>Check your course progress and performance in the course dashboard.</p>
        </div>
    </c:if>
</body>
</html>
