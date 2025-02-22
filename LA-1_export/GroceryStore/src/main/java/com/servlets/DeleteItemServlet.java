package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection
    String url = "jdbc:mysql://localhost:3306/grocery_store_db";
    String username = "root";
    String password = "A2Zlegacy@3";

    public DeleteItemServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve data from request parameters
        int itemId = Integer.parseInt(request.getParameter("itemId")); // Example parameter for item ID

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // Perform SQL DELETE operations
            // Example SQL statement for deleting an item
            String sql = "DELETE FROM Items WHERE ItemID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            statement.executeUpdate();

            connection.close();
            
         // Redirect to the inventory page after deletion
            response.sendRedirect("shopkeeper_inventory.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
    }
}
