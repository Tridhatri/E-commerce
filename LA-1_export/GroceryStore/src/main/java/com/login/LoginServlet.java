//package com.login;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
///**
// * Servlet implementation class LoginServlet
// */
//public class LoginServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public LoginServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		
//		
//		 String url = "jdbc:mysql://localhost:3306/grocery_store_db";
//		    String dbUsername = "root";
//		    String dbPassword = "A2Zlegacy@3";
//
//		    try {
//		        Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//		        PreparedStatement shopkeeperStatement = connection.prepareStatement("SELECT * FROM shopkeepers WHERE username=? AND password=?");
//		        shopkeeperStatement.setString(1, username);
//		        shopkeeperStatement.setString(2, password);
//		        ResultSet shopkeeperResult = shopkeeperStatement.executeQuery();
//		        HttpSession session = request.getSession();
//		        PrintWriter out = response.getWriter();
//		        if (shopkeeperResult.next()) {
//		            // Shopkeeper authenticated
//		            session.setAttribute("userType", "shopkeeper");
//		            session.setAttribute("username", username);
//		            out.println("Shopkeeper data found: " + shopkeeperResult.getString("username")); // Print the username to the web page
//		            response.sendRedirect("shopkeeper_home.jsp"); // Redirect to shopkeeper's home page
//		        } else {
//		            PreparedStatement buyerStatement = connection.prepareStatement("SELECT * FROM buyers WHERE username=? AND password=?");
//		            buyerStatement.setString(1, username);
//		            buyerStatement.setString(2, password);
//		            ResultSet buyerResult = buyerStatement.executeQuery();
//
//		            if (buyerResult.next()) {
//		                // Buyer authenticated
//		                session.setAttribute("userType", "buyer");
//		                session.setAttribute("username", username);
//		                out.println("Buyer data found: " + buyerResult.getString("username")); // Print the username to the web page
//		                response.sendRedirect("buyer_home.jsp"); // Redirect to buyer's home page
//		            } else {
//		                // Invalid credentials
//		                response.sendRedirect("login_error.jsp"); // Redirect to an error page
//		            }
//		        }
//
//		        connection.close();
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		        response.sendRedirect("login_error.jsp"); // Redirect to an error page
//		    }
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//
//
//}


package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String url = "jdbc:mysql://localhost:3306/grocery_store_db";
        String dbUsername = "root";
        String dbPassword = "A2Zlegacy@3";

        try {
        	Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            PreparedStatement shopkeeperStatement = connection.prepareStatement("SELECT * FROM shopkeepers WHERE username=? AND password=?");
            shopkeeperStatement.setString(1, username);
            shopkeeperStatement.setString(2, password);
            ResultSet shopkeeperResult = shopkeeperStatement.executeQuery();
            HttpSession session = request.getSession();
            PrintWriter out = response.getWriter();
            if (shopkeeperResult.next()) {
                // Shopkeeper authenticated
                session.setAttribute("userType", "shopkeeper");
                session.setAttribute("username", username);
                out.println("Shopkeeper data found: " + shopkeeperResult.getString("username")); // Print the username to the web page
                response.sendRedirect("shopkeeper_home.jsp"); // Redirect to shopkeeper's home page
            } else {
                PreparedStatement buyerStatement = connection.prepareStatement("SELECT * FROM buyers WHERE username=? AND password=?");
                buyerStatement.setString(1, username);
                buyerStatement.setString(2, password);
                ResultSet buyerResult = buyerStatement.executeQuery();

                if (buyerResult.next()) {
                    // Buyer authenticated
                    session.setAttribute("userType", "buyer");
                    session.setAttribute("username", username);
                    out.println("Buyer data found: " + buyerResult.getString("username")); // Print the username to the web page
                    response.sendRedirect("buyer_home.jsp"); // Redirect to buyer's home page
                } else {
                    // Invalid credentials
                    response.sendRedirect("login_error.jsp"); // Redirect to an error page
                }
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login_error.jsp"); // Redirect to an error page
        }
    }
}




