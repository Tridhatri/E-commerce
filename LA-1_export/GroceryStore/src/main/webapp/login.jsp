<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
 -->
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title> login page </title>
  <link rel="stylesheet" href="login2-style.css">
  <link rel="stylesheet" href="style-login.css">
</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="left">
    <div class="header">
      <h2 class="animation a1">Welcome Back Web-Slinger!</h2>
      <h4 class="animation a2">Log in to your account using username and password</h4>
    </div>
    <form action="LoginServlet" method="post">
      <input type="text" id="username" name="username" required class="form-field animation a3" placeholder="Username">
      <input type="password" id="password" name="password" required class="form-field animation a4" placeholder="Password">
      <p class="animation a5"></p>
      <button class="animation a6">LOGIN</button>
      <br>
      <br>
      <a href="signup.html" class="animation a6">New to the Spider Verse? Sign up here!</a>
     
    </form>
  </div>
  <div class="right"></div>
</div>
<!-- partial -->
  
</body>
</html>

 