package com.example.ongajong.fastoutv3.EditItem;

/**
 * Created by ongajong on 12/7/2017.
 */

public class Product {
    private String itemId;
    private String name;
    //private String description;
    private double price;
    private Integer quantity;

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double Price){
        this.price = price;
    }
    public String getProductId(){return itemId;}
    public String getName(){return name;}
    // public String getDescription(){return description+"\n";}
    public double getPrice(){return price;}
    public Integer getQuantity(){return quantity;}
    public Product(){

    }
    public Product(String productId, String name, double price, Integer quantity){
        this.itemId = productId;
        this.name = name;
        this.price = price;
        this.quantity= quantity;
    }
}
