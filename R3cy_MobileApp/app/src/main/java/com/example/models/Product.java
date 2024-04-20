package com.example.models;

public class Product {
    private int ProductID;
    private String ProductName;
    private double ProductPrice;
    private String ProductDescription;
    private byte[] ProductThumb;
    private int Hot;
    private String Category;
    private int Inventory;
    private double ProductRate;
    private double SalePrice;
    private int SoldQuantity;
    private String CreatedDate;
    private int Status;


//    constructor


    public Product(int productID, String productName, double productPrice, String productDescription, byte[] productThumb, int hot, String category, int inventory, double productRate, double salePrice, int soldQuantity, String createdDate, int status) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductDescription = productDescription;
        ProductThumb = productThumb;
        Hot = hot;
        Category = category;
        Inventory = inventory;
        ProductRate = productRate;
        SalePrice = salePrice;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public byte[] getProductThumb() {
        return ProductThumb;
    }

    public void setProductThumb(byte[] productThumb) {
        ProductThumb = productThumb;
    }

    public int getHot() {
        return Hot;
    }

    public void setHot(int hot) {
        Hot = hot;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }

    public double getProductRate() {
        return ProductRate;
    }

    public void setProductRate(double productRate) {
        ProductRate = productRate;
    }

    public double getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(double salePrice) {
        SalePrice = salePrice;
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