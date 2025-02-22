package com.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class AddImage
 */
public class AddImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			String itemName = request.getParameter("ItemName");
	        String itemDescription = request.getParameter("Description");
	        String quantityParam = request.getParameter("Quantity");
	        String priceParam = request.getParameter("Price");
	        String seller = request.getParameter("seller");
	        // Check for null values
	        int itemQuantity = quantityParam != null ? Integer.parseInt(quantityParam) : 0;
	        float price = priceParam != null ? Float.parseFloat(priceParam) : 0.0f;
	        
		System.out.println("in do post method of add image sevlet");
		Part file = request.getPart("image");
		String imageFilename =  file.getSubmittedFileName();
		System.out.println("THe image file name is " + imageFilename);
		
		//String uploadPath = "/Users/tridhatri/Desktop/lab assignment it/uploaded_images/"+imageFilename;
		String uploadPath = "/Users/tridhatri/Desktop/lab assignment it/src/main/webapp/uploaded_images/"+imageFilename; //working fine but refresh refresh
		//String uploadPath = "GroceryStore/.metadata/.plugins/org.eclipse.wst.server.core/tmp 0/webapps/uploaded_images"+imageFilename;
		System.out.println("upload path : " + uploadPath);
		
		try
		{
		
		FileOutputStream fos=new FileOutputStream(uploadPath);
		InputStream is=file.getInputStream();
		
		byte[] data=new byte[is.available()];
		is.read(data);
		fos.write(data);
		fos.close();
		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//**********************
		
		//getting database connection (jdbc code)
		Connection connection=null;
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery_store_db","root","A2Zlegacy@3");
			PreparedStatement stmt;
			String query= "INSERT INTO Items (ItemName, Description, Quantity, Price, ImageURL,seller) VALUES (?, ?, ?, ?, ?,?)";;
			stmt=connection.prepareStatement(query);
			 stmt.setString(1, itemName);
	            stmt.setString(2, itemDescription);
	            stmt.setInt(3, itemQuantity);
	            stmt.setFloat(4, price);
			stmt.setString(5,imageFilename);
			stmt.setString(6, seller);
			int row=stmt.executeUpdate(); // it returns no of rows affected.
			
			if(row>0)
			{
				System.out.println("Image added into database successfully.");
			}
			
			else
			{
				System.out.println("Failed to upload image.");
			}
			
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
	}

	}
