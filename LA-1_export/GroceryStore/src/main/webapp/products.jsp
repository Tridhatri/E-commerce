<%@ page import="java.util.List" %>
<%@ page import="com.dao.Item" %>
<%@ page import="com.dao.DatabaseManager" %>
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
        
        
        
          /* Additional styles for the search bar */
        .search-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .search-container input[type="text"] {
            padding: 10px;
            border-radius: 5px;
        }

        .search-container select {
            padding: 10px;
            border-radius: 5px;
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
    


<!-- Your existing code here -->

<!-- Search bar container -->
<div class="search-container">
    <input type="text" id="searchQuery" name="searchQuery" placeholder="Search with no.of items you want to fetch">
    <select id="itemsPerPage" name="itemsPerPage">
        <option value="5">5</option>
        <option value="10" selected>10</option>
        <option value="20">20</option>
    </select>
    <button onclick="searchItems()">Search</button>
</div>

<!-- Placeholder for displaying search results -->
<div id="searchResults"></div>

<!-- Script for handling search functionality -->
<script>
    function searchItems() {
        const searchQuery = document.getElementById('searchQuery').value;
        const itemsPerPage = document.getElementById('itemsPerPage').value;
        
        // Make an AJAX request to the servlet
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("searchResults").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "SearchServlet?query=" + searchQuery + "&itemsPerPage=" + itemsPerPage, true);
        xhttp.send();
    }
</script>












    <div class="container">
     <div style="display: flex; justify-content: flex-end; margin-bottom: 20px;">
            <a href="orders.jsp">
                <button>Go to Your Orders</button>
            </a>
            </div>
        
        <h3>Products</h3>
        
        <% // Pagination setup
    int itemsPerPage = 10; // Default number of items per page
    if (request.getParameter("itemsPerPage") != null) {
        itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
    }

    // Ensure that the page variable is not redeclared
    int page_no = 1; // Default page
    if (request.getParameter("page_no") != null) {
        page_no = Integer.parseInt(request.getParameter("page_no"));
    }

    DatabaseManager dbManager = new DatabaseManager();
    List<Item> items = dbManager.fetchItemsFromDatabase();

    int start = (page_no - 1) * itemsPerPage;
    int end = Math.min(start + itemsPerPage, items.size());
    %>
        
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Item Name</th>
                <th>Photo</th>
                <th>Description</th>
                <th>Stock Available</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
          
        <% for (int i = start; i < end; i++) {
            Item item = items.get(i);
        %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getName() %></td>
                <td><img src="uploaded_images/<%= item.getImageUrl() %>" width="100" height="100"></td>
                <td><%= item.getDescription() %></td>
                <td><%= item.getQuantity() %></td>
                <td><%= item.getPrice() %></td>
                <td>
                    <form action="AddToCartServlet" method="post">
                        <input type="hidden" name="itemId" value="<%= item.getId() %>">
                        <input type="hidden" name="itemName" value="<%= item.getName() %>">
                        <input type="hidden" name="price" value="<%= item.getPrice() %>">
                        <input type="hidden" name="imageURL" value='uploaded_images/<%= item.getImageUrl() %>'>
                        <button type="submit">Add to Cart</button>
                    </form>
                </td>
            </tr>
            <%
            }
            %>
        </table>
     
    <form action="" method="get">
        <label for="itemsPerPage">Items per Page:</label>
        <select name="itemsPerPage" id="itemsPerPage">
         	<option value="5" <% if (itemsPerPage == 5) { %> selected <% } %>>5</option>
            <option value="10" <% if (itemsPerPage == 10) { %> selected <% } %>>10</option>
            <option value="20" <% if (itemsPerPage == 20) { %> selected <% } %>>20</option>
            <option value="30" <% if (itemsPerPage == 30) { %> selected <% } %>>30</option>
        </select>
        <button type="submit">Apply</button>
    </form>

    <% // Pagination links
    int totalPages = (int) Math.ceil((double) items.size() / itemsPerPage);
    %>

    <div>
        <% if (page_no > 1) { %>
            <a href="?page_no=<%= page_no - 1 %>&itemsPerPage=<%= itemsPerPage %>">Previous</a>
        <% } %>

        <% for (int i = 1; i <= totalPages; i++) { %>
            <a href="?page_no=<%= i %>&itemsPerPage=<%= itemsPerPage %>"><%= i %></a>
        <% } %>

        <% if (page_no < totalPages) { %>
            <a href="?page_no=<%= page_no + 1 %>&itemsPerPage=<%= itemsPerPage %>">Next</a>
        <% } %>
    </div>
    </div>
</body>
</html>
