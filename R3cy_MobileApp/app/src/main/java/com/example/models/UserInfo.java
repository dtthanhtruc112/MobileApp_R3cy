package com.example.models;

import java.io.Serializable;

public class UserInfo implements Serializable {

    String fullName;
    String userName;
    String phone;
    String gender;
    String  birthday;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    String email;

    int customerid;

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public UserInfo(String fullName, String userName, String phone, String gender, String birthday, String email, int customerid) {
//        this.fullName = fullName;
//        this.userName = userName;
//        this.phone = phone;
//        this.gender = gender;
//        this.birthday = birthday;
//        this.email = email;
//        this.customerid = customerid;
//    }


//    public UserInfo(String username, String fullName, String email, String phone, String birthday) {
//        this.username = username;
//        this.fullName = fullName;
//        this.email = email;
//        this.phone = phone;
//        this.birthday = birthday;
//    }
}
