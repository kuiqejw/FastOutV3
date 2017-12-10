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
    public String getName(){return name;}
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){return price;}
    public String getItemId(){return itemId;}
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public Integer getQuantity(){return quantity;}
    public void setQuantity(Integer quantity){this.quantity = quantity;}

    public Product(){
    }
    public Product(String productId, String name, double price, Integer quantity){
        this.itemId = productId;
        this.name = name;
        this.price = price;
        this.quantity= quantity;
    }
}
