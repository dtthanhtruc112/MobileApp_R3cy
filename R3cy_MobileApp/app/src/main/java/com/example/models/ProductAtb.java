package com.example.models;

public class ProductAtb {
    private int ProductID;
    private double ProductPrice;
    private int Hot;
    private int Inventory;
    private int SoldQuantity;
    private String CreatedDate;
    private int Status;

    public ProductAtb(){}


    public ProductAtb(int productID, double productPrice, int hot, int inventory, int soldQuantity, String createdDate, int status) {
        ProductID = productID;
        ProductPrice = productPrice;
        Hot = hot;
        Inventory = inventory;
        SoldQuantity = soldQuantity;
        CreatedDate = createdDate;
        Status = status;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public int getHot() {
        return Hot;
    }

    public void setHot(int hot) {
        Hot = hot;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }

    public int getSoldQuantity() {
        return SoldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        SoldQuantity = soldQuantity;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
