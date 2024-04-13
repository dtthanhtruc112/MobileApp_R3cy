package com.example.models;

public class Customer {
    int customerId;
    String username;
    String fullName;
    int gender;
    String email;
    String phone;
    String password;
    int membershipScore;
    String birthday;
    byte[] customerThumb;
    String customerType;

    public Customer(int customerId, String username, String fullName, int gender, String email, String phone, String password, int membershipScore, String birthday, byte[] customerThumb, String customerType) {
        this.customerId = customerId;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.membershipScore = membershipScore;
        this.birthday = birthday;
        this.customerThumb = customerThumb;
        this.customerType = customerType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMembershipScore() {
        return membershipScore;
    }

    public void setMembershipScore(int membershipScore) {
        this.membershipScore = membershipScore;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public byte[] getCustomerThumb() {
        return customerThumb;
    }

    public void setCustomerThumb(byte[] customerThumb) {
        this.customerThumb = customerThumb;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
