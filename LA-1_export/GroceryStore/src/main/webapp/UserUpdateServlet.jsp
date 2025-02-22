<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>

<%
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        String url = "jdbc:mysql://localhost:3306/grocery_store_db";
        String dbUsername = "root";
        String dbPassword = "A2Zlegacy@3";

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, dbUsername, dbPassword);

        String checkQuery = "SELECT * FROM shopkeepers WHERE username=? OR email=?";
        statement = connection.prepareStatement(checkQuery);
        statement.setString(1, username);
        statement.setString(2, email);
        resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Handle the case of duplicate username or email
            out.println("Username or email already exists. Please use different credentials.");
            
            // Print the error message
            %>
            <form action="signup.html">
            <button type="submit">Back to Sign up</button>
        	</form>
        	<% 
        } else {
            if (userType.equals("shopkeeper")) {
                String shopkeeperQuery = "INSERT INTO shopkeepers (username, email, password) VALUES (?, ?, ?)";
                statement = connection.prepareStatement(shopkeeperQuery);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.executeUpdate();
            } else if (userType.equals("buyer")) {
                String buyerQuery = "INSERT INTO buyers (username, email, password) VALUES (?, ?, ?)";
                statement = connection.prepareStatement(buyerQuery);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.executeUpdate();
            }
            response.sendRedirect("signup_success.jsp"); // Redirect to a success page
        }
    } catch (ClassNotFoundException | SQLException e) {
        out.println("An error occurred: <br>");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        out.println(stringWriter.toString());
        response.sendRedirect("signup_error.jsp"); // Redirect to an error page
    } finally {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>
