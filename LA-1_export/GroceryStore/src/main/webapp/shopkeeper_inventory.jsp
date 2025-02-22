

<%@ page import="java.util.List" %>
<%@ page import="com.dao.Item" %>
<%@ page import="com.dao.DatabaseManager" %>
<%@ page import="org.apache.commons.io.*" %>
<%@ page import="org.apache.commons.fileupload2.jakarta.*" %>
<%@ page import="org.apache.commons.fileupload2.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spiderman's Grocery Store</title>
       
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
            width: 90%;
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px;
        }

        form {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 20px;
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

        input[type="text"], input[type="number"], textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ddd;
            box-sizing: border-box;
        }

        input[type="file"] {
            margin-bottom: 20px;
        }

        input[type="submit"] {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        img {
            border-radius: 5px;
            max-width: 100px;
            max-height: 100px;
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
        <h3>Add New items</h3>
        
           <form action="AddImage" method="post" enctype="multipart/form-data">
Item Name: <input type="text" name="ItemName"><br><br>
    Description: <textarea name="Description"></textarea><br><br>
    Quantity: <input type="number" name="Quantity"><br><br>
    Price: <input type="number" step="0.01" name="Price"><br><br>
    Image: <input type="file" name="image"><br><br>
   <input type="hidden" name="seller" value="${sessionScope.username}">
    <input type="submit" value="Add Item">
</form>




    <div class="container">
        <h3>Inventory</h3>
        <h4>If you cannot see your uploaded image, please wait for 10 seconds and reload the page</h4>
            <table border="1">
    <tr>
        <th>ID</th>
        <th>Item Name</th>
        <th>Quantity</th>
        <th>Description</th>
        <th>Image</th>
        <th>Price</th>
        <th>Seller/Shopkeeper's Name</th>
        <th>Action</th>
    </tr>
    <%
    DatabaseManager dbManager = new DatabaseManager(); // Create an instance of the DatabaseManager class
    List<Item> items = dbManager.fetchItemsFromDatabase(); // Retrieve items from the database
    for (Item item : items) {
    %>
    <tr>
        <td><%= item.getId() %></td>
        <td><%= item.getName() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getDescription() %></td>
        <td><img src="uploaded_images/<%= item.getImageUrl() %>" width="100" height="100"></td>

          <% System.out.println("the image url in sql database is : " + item.getImageUrl()); %> <!-- Print the URL on the console -->
        <td><%= item.getPrice() %></td>
        <td><%= item.getSeller() %></td>
        <td>
            <h3>Edit Item</h3>
            <form action="EditItemServlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                Item Name: <input type="text" name="ItemName" value="<%= item.getName() %>"><br><br>
                Description: <textarea name="Description"><%= item.getDescription() %></textarea><br><br>
                Quantity: <input type="number" name="Quantity" value="<%= item.getQuantity() %>"><br><br>
                Price: <input type="number" name="Price" step="0.01" value="<%= item.getPrice() %>"><br><br>
   				Image: <input type="file" name="image"><br><br>
    			Seller: <input type="text" name="seller" value="<%= session.getAttribute("username") %>" disabled><br><br>
                <input type="submit" value="Save Changes">
            </form>
            <form action="DeleteItemServlet" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <button type="submit">Remove Item</button>
            </form>
        </td>
    </tr>
    <%
    }
    %>
</table>
        
    </div>

  
        
    </div>
</body>
</html>

