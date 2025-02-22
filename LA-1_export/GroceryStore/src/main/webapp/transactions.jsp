<%@ page import="java.util.List" %>
<%@ page import="com.dao.Order" %>


 <%
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); //TO prevent the back button to go to this page again after hitting logout

    if(session.getAttribute("username")==null){
        response.sendRedirect("login.jsp");
    }
 

    %>
    
     
<!DOCTYPE html>
<html>
<head>
    <title>Spiderman's Grocery Store - Transaction Successful</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 50%;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            
            margin: 20px;
            text-align: center;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Transaction Successful</h2>
        <p>Your purchase was successful. Thank you for shopping with us!</p>
        <h3>Summary of Items Purchased:</h3>
        <ul>
            <%
                // Display a brief summary of the purchased items
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                for (Order order : orders) {
            %>
            <li><strong><%= order.getName() %></strong> - Quantity: <%= order.getQuantity() %>, Price: $<%= order.getPrice() %></li>
            <%
                }
            %>
        </ul>
    </div>
     <form action ="Logout">
        <input type = "submit" value = "Logout" >
    </form>
</body>
</html>
