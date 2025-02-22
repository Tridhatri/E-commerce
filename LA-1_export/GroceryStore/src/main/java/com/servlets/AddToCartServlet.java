package com.servlets;

import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/grocery_store_db";
    private static final String username = "root";
    private static final String password = "A2Zlegacy@3";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String itemIdParam = request.getParameter("itemId");

        if (itemIdParam != null && !itemIdParam.isEmpty()) {
            int itemID = Integer.parseInt(itemIdParam);
        
        int Quantity = 1;
        String itemName = request.getParameter("itemName");
        
        
        // Update the quantity of the item in the Items table
        updateItemQuantity(itemID, Quantity);
        
        //int Quantity = 0;
//        String quantityString = request.getParameter("Quantity");
//        if (quantityString != null && !quantityString.isBlank()) {
//             Quantity = Integer.parseInt(quantityString);
//            // Use the 'quantity' variable here or remove it if not needed
//        }
        
        String imageURL = request.getParameter("imageURL");
        String priceString = request.getParameter("price");
        double price = 0.0;
        

        if (priceString != null && !priceString.trim().isEmpty()) {
            price = Double.parseDouble(priceString);
            
            
        } else {
            // Handle the case where priceString is null or empty
            // You can set a default value or perform necessary error handling
        }
       
        String buyer = (String) request.getSession().getAttribute("username");
        
        Date addedOn = new Date(System.currentTimeMillis());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // Insert the item into the wishlist table
            String sql = "INSERT INTO Orders (ItemID, ItemName,imageURL,Quantity,Price, Buyer, OrderDate) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemID);
            statement.setString(2, itemName);
            statement.setString(3, imageURL);
            statement.setInt(4, Quantity);
            statement.setDouble(5, price);
            statement.setString(6, buyer);
            statement.setDate(7, addedOn);
            statement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Redirect to the orders page after adding the item
        response.sendRedirect("orders.jsp");
    }
        
        
}
    private void updateItemQuantity(int itemID, int quantity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // Update the quantity in the Items table
            String updateSql = "UPDATE Items SET Quantity = Quantity - ? WHERE ItemID = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, itemID);
            updateStatement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
