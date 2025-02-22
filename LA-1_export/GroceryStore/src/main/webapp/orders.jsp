<%@ page import="java.util.List" %>
<%@ page import="com.dao.Order" %>
<%@ page import="com.dao.DatabaseManager" %>

<%@ page import="java.text.DecimalFormat" %>


<!DOCTYPE html>
<html>
<head>
    <title>Spiderman's Grocery Store - Orders</title>
    <!-- Add your CSS styling here -->
    <style>
        /* CSS for the Spiderman-themed page layout and styling */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        h3, h4 {
            color: #333;
        }

        h4 {
            margin-top: 0;
            margin-bottom: 20px;
        }

        img {
            border-radius: 5px;
            max-width: 100px;
            max-height: 100px;
        }

        button {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
        
        
          .total-cost {
            font-size: 24px;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border-radius: 5px;
            margin-top: 20px;
            text-align: center;
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
    
     <form action ="Logout">
        <input type = "submit" value = "Logout" >
    </form>
    <div class="container">
      <div style="display: flex; justify-content: flex-end; margin-bottom: 20px;">
              
         <a href="products.jsp">
            <button>Add More Items</button>
        </a>
            </div>
            
         <!--    
            <div style="display: flex; justify-content: flex-end; margin-bottom: 20px;">
    <a href="transactions.jsp">
        <button>Check Out</button>
    </a>
</div> -->

<!-- Add an ID to the form for easy reference -->

 <form id="checkoutForm" action ="transactions.jsp" method="post">
        <input type = "submit" value = "Check Out" >
    </form>

<script>
    // Get the form element by its ID
    const form = document.getElementById("checkoutForm");

    // Add an event listener for the submit event
    form.addEventListener("submit", function (event) {
        // Prevent the default form submission
        event.preventDefault();

        // Set the checkout parameter to true
        const action = form.getAttribute("action");
        form.setAttribute("action", `${action}?checkout=true`);

        // Submit the form
        form.submit();
    });
</script>



            
        <h3>Your Orders</h3>
        <table border="1">
            <tr>
                <th>Order ID</th>
                <th>Item ID</th>
                <th>Item Name</th>
                <th>Photo</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Buyer's Name</th>
                <th>Order Date</th>
                <th>Remove Items</th>
            </tr>
            <%
            String buyer = (String) request.getSession().getAttribute("username");
            DatabaseManager dbManager = new DatabaseManager();
            List<Order> orders = dbManager.fetchOrdersByBuyer(buyer);
            float totalCost = dbManager.fetchTotalCostByBuyer(buyer);
            DecimalFormat df = new DecimalFormat("#.##");
        
            for (Order order : orders) {
            %>
           <tr>
    <td><%= order.getOrderID() %></td>
    <td><%= order.getId() %></td>
    <td><%= order.getName() %></td>
    <td> <img src="uploaded_images/<%= order.getImageUrl() %>" width="100" height="100">
    </td>
    <td><%= order.getQuantity() %></td>
    <td><%= order.getPrice() %></td>
    <td><%= order.getBuyer() %></td>
    <td><%= order.getOrderDate() %></td>
    <td>
                    <form action="RemoveFromOrdersServlet" method="post">
                        <input type="hidden" name="orderID" value="<%= order.getOrderID() %>">
                        <input type="hidden" name="itemID" value="<%= order.getId() %>">
                        <button type="submit">Remove Item</button>
                    </form>
                </td>
</tr>

          <%-- <img src="uploaded_images/<%= order.getImageUrl() %>" width="100" height="100">  --%>
            <%
            }
            %>
        </table>
        
    
        
    </div>
    <div class="total-cost">
            <h3>Total Cost: Rs.<%= df.format(totalCost) %></h3>
        </div>
<% 
    String checkout = request.getParameter("checkout");
    if (checkout != null && checkout.equals("true")) {
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }
%>
</body>
</html>
