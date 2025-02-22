<%@ page import="java.util.List" %>
<%@ page import="com.dao.ReportData" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shopkeeper Report</title>
    <style>
        /* Add your CSS styling here */
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Shopkeeper Report</h2>
    <table>
        <tr>
            <th>Item ID</th>
            <th>Item Name</th>
            <th>Buyer</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Order Date</th>
        </tr>
        <%
            List<ReportData> reportData = (List<ReportData>) request.getAttribute("items");
            for (ReportData data : reportData) {
        %>
        <tr>
            <td><%= data.getItemID() %></td>
            <td><%= data.getItemName() %></td>
            <td><%= data.getBuyer() %></td>
            <td><%= data.getQuantity() %></td>
            <td><%= data.getPrice() %></td>
            <td><%= data.getOrderDate() %></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
