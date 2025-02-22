package com.dao;

import java.sql.Date;

public class Order {
	private int OrderID;
    private int ItemID;
    private String ItemName;
    private String ImageURL;
    private int Quantity;
   private float price;
    private String buyer;
    private Date OrderDate;
  
    // Constructors, getters, and setters
    public Order(int OrderID,int itemId,String itemName, int quantity,String imageUrl, Float price,String buyer,Date OrderDate) {
    	this.OrderID = OrderID;
    	this.ItemID = itemId;
        this.ItemName = itemName;
        this.Quantity = quantity;
        this.ImageURL = imageUrl;
        this.price = price;
        this.buyer = buyer;
        this.OrderDate = OrderDate;
    }

    public int getOrderID() {
        return OrderID;
    }
    
    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }
    public int getId() {
        return ItemID;
    }

    public void setId(int itemId) {
        this.ItemID = itemId;
    }
    // Getters and setters for all fields
    public String getName() {
        return ItemName;
    }
    

    public void setName(String itemName) {
        this.ItemName = itemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    
    
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return ImageURL;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageURL = imageUrl;
    }
    public String getBuyer() {
        return buyer;
    }
    public void setSeller(String buyer) {
        this.buyer = buyer;
    }
    public Date getOrderDate() {
    	return OrderDate;
    }
    public void setOrderDate(Date OrderDate) {
    	this.OrderDate= OrderDate;
    }
}
