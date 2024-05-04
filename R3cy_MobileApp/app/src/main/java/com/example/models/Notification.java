package com.example.models;

public class Notification {
    private int notificationId;
    private String header;
    private String content;
    private int customerId;
    private String createdAt;

    public Notification() {
        // Constructor mặc định
    }

    public Notification(int notificationId, String header, String content, int customerId, String createdAt) {
        this.notificationId = notificationId;
        this.header = header;
        this.content = content;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
