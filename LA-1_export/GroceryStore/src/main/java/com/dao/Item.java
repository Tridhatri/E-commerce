package com.dao;

public class Item {
	private int itemId;
    private String itemName;
    private int quantity;
    private String description;
    private Float price;
    private String imageUrl;
    private String seller;
  
    // Constructors, getters, and setters
    public Item(int itemId,String itemName, int quantity, String description, String imageUrl, Float price,String seller) {
    	this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.seller = seller;
    }

    
    public int getId() {
        return itemId;
    }

    public void setId(int itemId) {
        this.itemId = itemId;
    }
    // Getters and setters for all fields
    public String getName() {
        return itemName;
    }
    

    public void setName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }

    
}
