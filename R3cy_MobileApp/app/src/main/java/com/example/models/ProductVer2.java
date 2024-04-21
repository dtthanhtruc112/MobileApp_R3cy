package com.example.models;

public class ProductVer2 {
    private int ProductID;
    private String ProductName;
    private byte[] ProductThumb;
    private String Category;
    private double ProductRate;
    private double SalePrice;

    public ProductVer2(int productID, String productName, byte[] productThumb, String category, double productRate, double salePrice) {
        ProductID = productID;
        ProductName = productName;
        ProductThumb = productThumb;
        Category = category;
        ProductRate = productRate;
        SalePrice = salePrice;
    }

    public double getProductRate() {
        return ProductRate;
    }

    public void setProductRate(double productRate) {
        ProductRate = productRate;
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

    public byte[] getProductThumb() {
        return ProductThumb;
    }

    public void setProductThumb(byte[] productThumb) {
        ProductThumb = productThumb;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(double salePrice) {
        SalePrice = salePrice;
    }
}
