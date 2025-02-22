package com.dao;
import com.dao.DatabaseManager;
import com.dao.Item;
import java.util.List;

public class DatabaseManagerTest {
    public static void main(String[] args) {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            List<Item> items = dbManager.fetchItemsFromDatabase();

            if (items.isEmpty()) {
                System.out.println("No items found in the database.");
            } else {
                System.out.println("Items retrieved from the database:");
                for (Item item : items) {
                    System.out.println("Name: " + item.getName());
                    System.out.println("Quantity: " + item.getQuantity());
                    System.out.println("Description: " + item.getDescription());
                    System.out.println("Image URL: " + item.getImageUrl());
                    System.out.println("Price: " + item.getPrice());
                    System.out.println("Seller: " + item.getSeller());
                    System.out.println("-------------------------");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
