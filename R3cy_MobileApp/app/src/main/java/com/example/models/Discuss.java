package com.example.models;

public class Discuss {
    int DiscussID;
    int DiscussProductID;
    String DiscussCustomerEmail;
    String DiscussContent;
    String DiscussRespond;
    int DiscussStatus;

    public Discuss(int discussID, int discussProductID, String discussCustomerEmail, String discussContent, String discussRespond, int discussStatus) {
        DiscussID = discussID;
        DiscussProductID = discussProductID;
        DiscussCustomerEmail = discussCustomerEmail;
        DiscussContent = discussContent;
        DiscussRespond = discussRespond;
        DiscussStatus = discussStatus;
    }

    public int getDiscussID() {
        return DiscussID;
    }

    public void setDiscussID(int discussID) {
        DiscussID = discussID;
    }

    public int getDiscussProductID() {
        return DiscussProductID;
    }

    public void setDiscussProductID(int discussProductID) {
        DiscussProductID = discussProductID;
    }

    public String getDiscussCustomerEmail() {
        return DiscussCustomerEmail;
    }

    public void setDiscussCustomerEmail(String discussCustomerEmail) {
        DiscussCustomerEmail = discussCustomerEmail;
    }

    public String getDiscussContent() {
        return DiscussContent;
    }

    public void setDiscussContent(String discussContent) {
        DiscussContent = discussContent;
    }

    public String getDiscussRespond() {
        return DiscussRespond;
    }

    public void setDiscussRespond(String respondContent) {
        DiscussRespond = respondContent;
    }

    public int getDiscussStatus() {
        return DiscussStatus;
    }

    public void setDiscussStatus(int discussStatus) {
        DiscussStatus = discussStatus;
    }
}
