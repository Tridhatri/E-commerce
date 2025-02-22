<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopkeeper Home</title>
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
    <%
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); //TO prevent the back button to go to this page again after hitting logout

    if(session.getAttribute("username")==null){
        response.sendRedirect("login.jsp");
    }
    %>
    <h2>Welcome ${username}, you are logged in as a Shopkeeper!</h2>

    <div class="dashboard-button">
        <form action="shopkeeper_inventory.jsp?username=${username}">
            <input type="submit" value="Access Dashboard">
        </form>
    </div>

    <div class="logout-button">
        <form action="Logout">
            <input type="submit" value="Logout">
        </form>
    </div>

    <div class="report-form">
        <form action="ReportServlet" method="post">
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate">
            <br><br>
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate">
            <br><br>
            <input type="submit" value="Generate Report">
        </form>
    </div>
</body>
</html>
