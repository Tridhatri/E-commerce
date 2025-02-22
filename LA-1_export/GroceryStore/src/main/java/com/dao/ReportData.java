package com.dao;

import java.sql.Date;

public class ReportData {
    private int itemID;
    private String itemName;
    private String buyer;
    private int quantity;
    private float price;
    private Date orderDate;

    public ReportData(int itemID, String itemName, String buyer, int quantity, float price, Date orderDate) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.buyer = buyer;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getBuyer() {
        return buyer;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
