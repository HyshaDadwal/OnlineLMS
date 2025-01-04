<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="/user" method="post">
        <input type="hidden" name="action" value="login">
        <label for="username">username:</label>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br>
        <button type="submit">Login</button>
    </form>
    <p>
        <a href="register.html">Register</a>
    </p>
    <p style="color: red;">
        <!-- Display error message -->
        <c:if test="${not empty param.error}">
            ${param.error}
        </c:if>
    </p>
</body>
</html>