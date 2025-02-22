//package com.servlets;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//
//public class EditItemServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    // Database connection
//    String url = "jdbc:mysql://localhost:3306/grocery_store_db";
//    String username = "root";
//    String password = "A2Zlegacy@3";
//
//    public EditItemServlet() {
//        super();
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int itemId = 0;
//        if (request.getParameter("itemId") != null) {
//            itemId = Integer.parseInt(request.getParameter("itemId"));
//        }
//
//        // Additional parameters for editing an item
//        String itemName = request.getParameter("ItemName");
//        String itemDescription = request.getParameter("Description");
//        int itemQuantity = 0;
//        if (request.getParameter("Quantity") != null) {
//            itemQuantity = Integer.parseInt(request.getParameter("Quantity"));
//        }
//        float price = 0.0f;
//        if (request.getParameter("Price") != null) {
//            price = Float.parseFloat(request.getParameter("Price"));
//        }
//
//        
//        
//        Part filePart = request.getPart("image");
//        String fileName = filePart.getSubmittedFileName();
//        InputStream fileContent = filePart.getInputStream();
//
//        try (Connection conn = DriverManager.getConnection(url, username, password)) {
//            String sql = "UPDATE Items SET ItemName=?, Description=?, Quantity=?, Price=?, ImageURL=? WHERE ItemID=?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, itemName);
//            pstmt.setString(2, itemDescription);
//            pstmt.setInt(3, itemQuantity);
//            pstmt.setFloat(4, price);
//            pstmt.setString(5, fileName);
//            pstmt.setInt(6, itemId);
//            pstmt.executeUpdate();
//            
//            
//       
//        
//            
//            
//            //String uploadPath = "uploaded_images/" + fileName;
//            String uploadPath =  "/Users/tridhatri/Desktop/lab assignment it/src/main/webapp/uploaded_images/"+ fileName;
//            try (FileOutputStream fos = new FileOutputStream(uploadPath)) {
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = fileContent.read(buffer)) > 0) {
//                    fos.write(buffer, 0, length);
//                }
//            }
//            response.sendRedirect("shopkeeper_inventory.jsp");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}

package com.servlets;
//DId not add seller?
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class EditItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection
    String url = "jdbc:mysql://localhost:3306/grocery_store_db";
    String username = "root";
    String password = "A2Zlegacy@3";

    public EditItemServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = 0;
        if (request.getParameter("itemId") != null) {
            itemId = Integer.parseInt(request.getParameter("itemId"));
        }

        // Additional parameters for editing an item
        String itemName = request.getParameter("ItemName");
        String itemDescription = request.getParameter("Description");
        int itemQuantity = 0;
        if (request.getParameter("Quantity") != null) {
            itemQuantity = Integer.parseInt(request.getParameter("Quantity"));
        }
        float price = 0.0f;
        if (request.getParameter("Price") != null) {
            price = Float.parseFloat(request.getParameter("Price"));
        }

        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql;
            PreparedStatement pstmt;
            
            if (fileName != null && !fileName.isEmpty()) {
                // If a new image is selected during the edit, update the image
                sql = "UPDATE Items SET ItemName=?, Description=?, Quantity=?, Price=?, ImageURL=? WHERE ItemID=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, itemName);
                pstmt.setString(2, itemDescription);
                pstmt.setInt(3, itemQuantity);
                pstmt.setFloat(4, price);
                pstmt.setString(5, fileName);
                pstmt.setInt(6, itemId);
            } else {
                // If no new image is selected, exclude image from the update
                sql = "UPDATE Items SET ItemName=?, Description=?, Quantity=?, Price=? WHERE ItemID=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, itemName);
                pstmt.setString(2, itemDescription);
                pstmt.setInt(3, itemQuantity);
                pstmt.setFloat(4, price);
                pstmt.setInt(5, itemId);
            }

            pstmt.executeUpdate();

            if (fileName != null && !fileName.isEmpty()) {
                // Only update the image if a new image is selected
            	 String uploadPath =  "/Users/tridhatri/Desktop/lab assignment it/src/main/webapp/uploaded_images/"+ fileName; // WORKING BUT NEED TO REFRESH AGAIN AGAIN
            	//String uploadPath = "/Users/tridhatri/Desktop/lab assignment it/uploaded_images/"+fileName;
            	//String uploadPath = "GroceryStore/.metadata/.plugins/org.eclipse.wst.server.core/tmp 0/webapps/uploaded_images"+fileName;
                try (FileOutputStream fos = new FileOutputStream(uploadPath);
                     InputStream fileContent = filePart.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fileContent.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }
            }

            response.sendRedirect("shopkeeper_inventory.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
