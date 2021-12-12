<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Page register</title>
</head>
<body>
<form action="./toRegister" method="get">
    <div class="register" style="text-align: center;">
        <div class="register-header">
            <h1>Register</h1>
        </div>
        <div class="register-form">
            <h3>Username:</h3>
            <label>
                <input type="text" name="userName" placeholder="Username"/>
            </label>
            <br>
            <h3>Password:</h3>
            <label>
                <input type="password" name="password" placeholder="Password"/>
            </label>
            <br>
            <input type="submit" value="Register"/>
        </div>
    </div>
</form>
</body>
</html>
