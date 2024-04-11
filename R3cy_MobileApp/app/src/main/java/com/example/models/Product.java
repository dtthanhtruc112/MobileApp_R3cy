package com.example.models;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private byte[] productThumb;
    private double productPrice;
    private double salePrice;
    private String productDescription;
    private int inventory;
    private double productRate;
    private int soldQuantity;
    private int status;
    private byte[] productImg1;
    private byte[] productImg2;
    private byte[] productImg3;
    private String createdDate;
    private int hot;

//    constructor

    public Product(int productId, String productName, String category, byte[] productThumb, double productPrice, double salePrice, String productDescription, int inventory, double productRate, int soldQuantity, int status, byte[] productImg1, byte[] productImg2, byte[] productImg3, String createdDate, int hot) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.productThumb = productThumb;
        this.productPrice = productPrice;
        this.salePrice = salePrice;
        this.productDescription = productDescription;
        this.inventory = inventory;
        this.productRate = productRate;
        this.soldQuantity = soldQuantity;
        this.status = status;
        this.productImg1 = productImg1;
        this.productImg2 = productImg2;
        this.productImg3 = productImg3;
        this.createdDate = createdDate;
        this.hot = hot;
    }

//    getter and setter

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(byte[] productThumb) {
        this.productThumb = productThumb;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public double getProductRate() {
        return productRate;
    }

    public void setProductRate(double productRate) {
        this.productRate = productRate;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getProductImg1() {
        return productImg1;
    }

    public void setProductImg1(byte[] productImg1) {
        this.productImg1 = productImg1;
    }

    public byte[] getProductImg2() {
        return productImg2;
    }

    public void setProductImg2(byte[] productImg2) {
        this.productImg2 = productImg2;
    }

    public byte[] getProductImg3() {
        return productImg3;
    }

    public void setProductImg3(byte[] productImg3) {
        this.productImg3 = productImg3;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }
}