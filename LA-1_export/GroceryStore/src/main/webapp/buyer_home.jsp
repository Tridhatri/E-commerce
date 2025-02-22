<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buyer Home</title>
       <link rel="stylesheet" href="common.css"> 
        <style>
        body {
            background-color: #f5f5f5;
            font-family: Arial, sans-serif;
            text-align: center;
            padding-top: 50px;
        }
        h2 {
            font-size: 36px;
            font-weight: bold;
            color: #333;
            margin-bottom: 30px;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="submit"] {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            font-weight: bold;
            margin-top: 10px;
        }
        button[type="submit"] {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            font-weight: bold;
            margin-top: 10px;
        }
        label {
            font-weight: bold;
            font-size: 18px;
            margin-right: 10px;
        }
        input[type="date"] {
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 10px;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <!-- Add your buyer home page content here -->
    <%
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); //TO prevent the back button to go to this page again after hitting logout

    if(session.getAttribute("username")==null){
        response.sendRedirect("login.jsp");
    }
    %>
    <h2>Welcome ${username}, you are logged in as a Buyer !</h2>

    <form action ="Logout">
        <input type = "submit" value = "Logout" >
    </form>

    <form action="products.jsp" method="get">
        <button type="submit">SHOP</button>
    </form>
</body>
</html>
