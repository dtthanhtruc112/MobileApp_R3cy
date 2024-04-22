package com.example.models;

public class Feedback {
    int FeedbackID;
    int ProductID;
    int OrderID;
    int CustomerID;
    String FeedbackContent;
    double FeedbackRating;
    String Date;

    public Feedback(int feedbackID, int productID, int orderID, int customerID, String feedbackContent, double feedbackRating, String date) {
        FeedbackID = feedbackID;
        ProductID = productID;
        OrderID = orderID;
        CustomerID = customerID;
        FeedbackContent = feedbackContent;
        FeedbackRating = feedbackRating;
        Date = date;
    }

    public int getFeedbackID() {
        return FeedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        FeedbackID = feedbackID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getFeedbackContent() {
        return FeedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        FeedbackContent = feedbackContent;
    }

    public double getFeedbackRating() {
        return FeedbackRating;
    }

    public void setFeedbackRating(double feedbackRating) {
        FeedbackRating = feedbackRating;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
