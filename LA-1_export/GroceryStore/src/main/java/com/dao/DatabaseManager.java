package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




public class DatabaseManager {

    private final String url = "jdbc:mysql://localhost:3306/grocery_store_db";
    private final String dbUsername = "root";
    private final String dbPassword = "A2Zlegacy@3";

    public List<Item> fetchItemsFromDatabase() throws ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Items");

            while (resultSet.next()) {
                int itemID = resultSet.getInt("ItemID");
                String name = resultSet.getString("ItemName");
                String description = resultSet.getString("Description");
                int quantity = resultSet.getInt("Quantity");
                float price = resultSet.getFloat("Price");
                String imageUrl = resultSet.getString("ImageURL");
                String seller = resultSet.getString("seller");

                Item item = new Item(itemID, name, quantity, description, imageUrl, price, seller);
                items.add(item);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Order> fetchOrdersByBuyer(String buyer) throws ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT * FROM Orders WHERE Buyer=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, buyer);
            ResultSet resultSet = preparedStatement.executeQuery();
            
           


            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int itemID = resultSet.getInt("ItemID");
                String itemName = resultSet.getString("ItemName");
                int quantity = resultSet.getInt("Quantity");
                Float price = resultSet.getFloat("Price");
                Date orderDate = resultSet.getDate("OrderDate");

                String imageURL = fetchImageURL(itemID, connection);
                updateImageURLInOrders(orderID, imageURL, connection);
                
               
                

                Order order = new Order(orderID, itemID, itemName, quantity, imageURL, price, buyer, orderDate);
                orders.add(order);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private String fetchImageURL(int itemID, Connection connection) {
        String imageURL = "";
        try {
            String query = "SELECT Items.ImageURL FROM Items JOIN Orders ON Items.ItemID = Orders.ItemID WHERE Items.ItemID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, itemID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                imageURL = resultSet.getString("ImageURL");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageURL;
    }

    private void updateImageURLInOrders(int orderID, String imageURL, Connection connection) {
        try {
            String query = "UPDATE Orders SET ImageURL = ? WHERE OrderID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, imageURL);
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> searchItems(String query, int itemsPerPage) throws ClassNotFoundException {
        List<Item> searchResults = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "SELECT * FROM Items WHERE ItemName LIKE ? LIMIT ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + query + "%");
                preparedStatement.setInt(2, itemsPerPage);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int itemID = resultSet.getInt("ItemID");
                        String name = resultSet.getString("ItemName");
                        String description = resultSet.getString("Description");
                        int quantity = resultSet.getInt("Quantity");
                        float price = resultSet.getFloat("Price");
                        String imageUrl = resultSet.getString("ImageURL");
                        String seller = resultSet.getString("seller");

                        Item item = new Item(itemID, name, quantity, description, imageUrl, price, seller);
                        searchResults.add(item);
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
    
    
    
    
    
    public boolean removeItemFromOrders(int orderID, int itemID) {
        boolean removed = false;
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            // Get the quantity of the item to be removed
            String getQuantityQuery = "SELECT Quantity FROM Items WHERE ItemID = ?";
            PreparedStatement getQuantityStatement = connection.prepareStatement(getQuantityQuery);
            getQuantityStatement.setInt(1, itemID);
            ResultSet quantityResultSet = getQuantityStatement.executeQuery();
            if (quantityResultSet.next()) {
                int quantity = quantityResultSet.getInt("Quantity");
                // Increment the stock (Quantity field) in the Items table
                String updateStockQuery = "UPDATE Items SET Quantity = ? WHERE ItemID = ?";
                PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
                updateStockStatement.setInt(1, quantity + 1);
                updateStockStatement.setInt(2, itemID);
                updateStockStatement.executeUpdate();

                // Remove the item from the Orders table
                String removeItemQuery = "DELETE FROM Orders WHERE OrderID = ? AND ItemID = ?";
                PreparedStatement removeItemStatement = connection.prepareStatement(removeItemQuery);
                removeItemStatement.setInt(1, orderID);
                removeItemStatement.setInt(2, itemID);
                int rowsAffected = removeItemStatement.executeUpdate();
                if (rowsAffected > 0) {
                    removed = true;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removed;
    }
    
    
    
    public float fetchTotalCostByBuyer(String buyer) throws ClassNotFoundException {
        float totalCost = 0;
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT Price, Quantity FROM Orders WHERE Buyer=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, buyer);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                float price = resultSet.getFloat("Price");
                int quantity = resultSet.getInt("Quantity");
                totalCost += price * quantity;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCost;
    }

    
    
    public static List<ReportData> generateReport(String startDate, String endDate) {
        List<ReportData> reportData = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/grocery_store_db";
        String dbUsername = "root";
        String dbPassword = "A2Zlegacy@3";

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "SELECT items.ItemID, items.ItemName, Orders.Buyer, Orders.Quantity, Orders.Price, Orders.OrderDate " +
                    "FROM items JOIN Orders ON items.ItemID = Orders.ItemID " +
                    "WHERE Orders.OrderDate BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int itemID = resultSet.getInt("ItemID");
                String itemName = resultSet.getString("ItemName");
                String buyer = resultSet.getString("Buyer");
                int quantity = resultSet.getInt("Quantity");
                float price = resultSet.getFloat("Price");
                Date orderDate = resultSet.getDate("OrderDate");

                ReportData data = new ReportData(itemID, itemName, buyer, quantity, price, orderDate);
                reportData.add(data);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportData;
    }
    
    
    //generates by the specific seller 
    public static List<ReportData> generateReport(String startDate, String endDate, String seller) {
        List<ReportData> reportData = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/grocery_store_db";
        String dbUsername = "root";
        String dbPassword = "A2Zlegacy@3";

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "SELECT items.ItemID, items.ItemName, Orders.Buyer, Orders.Quantity, Orders.Price, Orders.OrderDate " +
                         "FROM items JOIN Orders ON items.ItemID = Orders.ItemID " +
                         "WHERE items.seller = ? AND Orders.OrderDate BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, seller);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int itemID = resultSet.getInt("ItemID");
                String itemName = resultSet.getString("ItemName");
                String buyer = resultSet.getString("Buyer");
                int quantity = resultSet.getInt("Quantity");
                float price = resultSet.getFloat("Price");
                Date orderDate = resultSet.getDate("OrderDate");

                ReportData data = new ReportData(itemID, itemName, buyer, quantity, price, orderDate);
                reportData.add(data);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportData;
    }

    
    
    public List<Item> fetchItemsForReport() {
        List<Item> items = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            
            String sql = "SELECT ItemID, ItemName FROM Items";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int itemID = resultSet.getInt("ItemID");
                String name = resultSet.getString("ItemName");
                // Add more fields as required
                String description = resultSet.getString("Description");
                int quantity = resultSet.getInt("Quantity");
                float price = resultSet.getFloat("Price");
                String imageUrl = resultSet.getString("ImageURL");
                String seller = resultSet.getString("seller");

                Item item = new Item(itemID, name, quantity, description, imageUrl, price, seller);
              
                items.add(item);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Order> fetchOrdersForReport() {
        List<Order> orders = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "SELECT Orders.OrderID, Orders.ItemID, Orders.Quantity, Orders.Price, Orders.OrderDate, Items.ItemName, Orders.Buyer FROM Orders JOIN Items ON Orders.ItemID = Items.ItemID";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int itemID = resultSet.getInt("ItemID");
                // Add more fields as required
                
              
                String itemName = resultSet.getString("ItemName");
                int quantity = resultSet.getInt("Quantity");
                Float price = resultSet.getFloat("Price");
                Date orderDate = resultSet.getDate("OrderDate");
                String buyer = resultSet.getString("buyer");
                String imageURL = fetchImageURL(itemID, connection);
                Order order = new Order(orderID, itemID, itemName, quantity, imageURL, price, buyer, orderDate);
              
                orders.add(order);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

}







//private String fetchImageURL(int imageID) {
//	String imageURL = "";
//    try {
//        Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//        String query = "SELECT ImageURL FROM Items WHERE imageID = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        preparedStatement.setInt(1, imageID);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        if (resultSet.next()) {
//            imageURL = resultSet.getString("ImageURL");
//        } else {
//            System.out.println("No image URL found for imageID: " + imageID);
//        }
//
//        connection.close();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    System.out.println("Fetched Image URL: " + imageURL); // Add this line for debugging
//    return imageURL;
//}



