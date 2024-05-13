package com.example.models;

public class order_rating {
    int OrderID;
    int OrderLineID;
    int ProductID;
    byte [] ProductImg;
    String Productname;
    String Productquantity;
    double Productprice;
    String Feedbackcontent;
    String Feedbackrating;
    int FeedbackID;

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    String OrderStatus;

    public order_rating(int orderID, int orderLineID, int productID, byte[] productImg, String productname, String productquantity, double productprice, String feedbackcontent, String feedbackrating, int feedbackID, String orderStatus) {
        OrderID = orderID;
        OrderLineID = orderLineID;
        ProductID = productID;
        ProductImg = productImg;
        Productname = productname;
        Productquantity = productquantity;
        Productprice = productprice;
        Feedbackcontent = feedbackcontent;
        Feedbackrating = feedbackrating;
        FeedbackID = feedbackID;
        OrderStatus = orderStatus;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getOrderLineID() {
        return OrderLineID;
    }

    public void setOrderLineID(int orderLineID) {
        OrderLineID = orderLineID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public byte[] getProductImg() {
        return ProductImg;
    }

    public void setProductImg(byte[] productImg) {
        ProductImg = productImg;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }

    public String getProductquantity() {
        return Productquantity;
    }

    public void setProductquantity(String productquantity) {
        Productquantity = productquantity;
    }

    public double getProductprice() {
        return Productprice;
    }

    public void setProductprice(double productprice) {
        Productprice = productprice;
    }

    public String getFeedbackcontent() {
        return Feedbackcontent;
    }

    public void setFeedbackcontent(String feedbackcontent) {
        Feedbackcontent = feedbackcontent;
    }

    public String getFeedbackrating() {
        return Feedbackrating;
    }

    public void setFeedbackrating(String feedbackrating) {
        Feedbackrating = feedbackrating;
    }

    public int getFeedbackID() {
        return FeedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        FeedbackID = feedbackID;
    }
}
