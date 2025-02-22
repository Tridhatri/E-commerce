package com.Groc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/grocery_store_db";
        String username = "root";
        String password = "A2Zlegacy@3";

        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Execute a query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM shopkeepers");

            // Process the result set
            while (resultSet.next()) {
                // Retrieve data from the result set and print it
                System.out.println("Column1: " + resultSet.getString("id"));
                System.out.println("Column2: " + resultSet.getString("username"));
                // Add more print statements for other columns as needed
            }

            // Clean-up environment
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection error!");
            e.printStackTrace();
        }
    }
}
