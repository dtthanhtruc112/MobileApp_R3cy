package com.example.models;

public class Product {
    private int ProductID;
    private byte[] ProductThumb;
    private String ProductName;
    private double SalePrice;
    private String Category;
    private String ProductDescription;
    private double ProductRate;


//    constructor
    public Product(){}

    public Product(int productID, byte[] productThumb, String productName, double salePrice, String category, String productDescription, double productRate) {
        ProductID = productID;
        ProductThumb = productThumb;
        ProductName = productName;
        SalePrice = salePrice;
        Category = category;
        ProductDescription = productDescription;
        ProductRate = productRate;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public byte[] getProductThumb() {
        return ProductThumb;
    }

    public void setProductThumb(byte[] productThumb) {
        ProductThumb = productThumb;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(double salePrice) {
        SalePrice = salePrice;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public double getProductRate() {
        return ProductRate;
    }

    public void setProductRate(double productRate) {
        ProductRate = productRate;
    }
}