//package com.Groc;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
//public class StockUpdateServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    // Database connection
//    String url = "jdbc:mysql://localhost:3306/grocery_store_db";
//    String username = "root";
//    String password = "A2Zlegacy@3";
//
//    public StockUpdateServlet() {
//        super();
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        PrintWriter out = response.getWriter();
//        out.print("it is working!");
//        // Retrieve data from request parameters
//        String itemName = request.getParameter("ItemName");
//        String itemDescription = request.getParameter("Description");
//        int itemQuantity = Integer.parseInt(request.getParameter("Quantity")); // Example parameter
//        float price = Float.parseFloat(request.getParameter("Price"));
//
//        String imageUrl = request.getParameter("Image");
//
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            // Perform CRUD operations
//            // Example of an SQL INSERT statement
//            String sql = "INSERT INTO Items (ItemName, Description, Quantity, Price,ImageURL) VALUES (?, ?, ?, ?,?)";
//            
// 
//            System.out.println("SQL Statement: " + sql); // Print the SQL statement before execution
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, itemName);
//            statement.setString(2, itemDescription);
//            statement.setInt(3, itemQuantity);
//            statement.setFloat(4, price);
//            statement.setString(5, imageUrl);
//            statement.executeUpdate();
//            // Forward to the inventory page after adding the item
//            //request.getRequestDispatcher("shopkeeper_inventory.jsp").forward(request, response);
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Add your GET method implementation here
//
//        // For example, you can handle GET requests for retrieving stock information or displaying the form for item creation.
//        PrintWriter out = response.getWriter();
//        out.println("This is the StockUpdateServlet. Use POST method for updating stock or creating items.");
//
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            // Perform CRUD operations here
//
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    
//}


package com.Groc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemFactory;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.*;
import org.apache.commons.fileupload2.jakarta.*;
import org.apache.commons.fileupload2.*;




public class StockUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection
    String url = "jdbc:mysql://localhost:3306/grocery_store_db";
    String username = "root";
    String password = "A2Zlegacy@3";
    String uploadDirectory = "uploaded_images";

    public StockUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 PrintWriter out = response.getWriter();
    	 out.print("it is working!");
        // Retrieve data from request parameters
        String itemName = request.getParameter("ItemName");
        String itemDescription = request.getParameter("Description");
        String quantityParam = request.getParameter("Quantity");
        String priceParam = request.getParameter("Price");

        // Check for null values
        int itemQuantity = quantityParam != null ? Integer.parseInt(quantityParam) : 0;
        float price = priceParam != null ? Float.parseFloat(priceParam) : 0.0f;
        
        
        try {
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // Perform CRUD operations
            String sql = "INSERT INTO Items (ItemName, Description, Quantity, Price, ImageURL) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, itemName);
            statement.setString(2, itemDescription);
            statement.setInt(3, itemQuantity);
            statement.setFloat(4, price);
            
         // Create directory if it doesn't exist
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            
            
            // Process image file
            Part filePart = request.getPart("imageFile");
            String fileName = filePart != null ? getFileName(filePart) : null;
            if (fileName != null) {
                String filePath = getServletContext().getRealPath("") + File.separator + uploadDirectory + File.separator + fileName;
                System.out.println("File Path for the image: " + filePath); // Print the file path
                filePart.write(filePath);
                filePart.write(filePath);
                statement.setString(5, uploadDirectory + "/" + fileName);
            } else {
                statement.setString(5, null); // or handle appropriately if no file is uploaded
            }
            
       
 
            
          
            // Redirect to the inventory page after adding the item
            response.sendRedirect("shopkeeper_inventory.jsp");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //response.sendRedirect("error_page.jsp"); // Create an error page to handle database errors
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //response.sendRedirect("error_page.jsp"); // Create an error page to handle number format errors
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
        
        
        
        

    private String getFileName(Part part) {
        if (part != null) {
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
        }
        return null;
    }

    // Rest of the code remains the same
}

