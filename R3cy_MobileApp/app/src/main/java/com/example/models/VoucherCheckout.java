package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class VoucherCheckout implements Serializable {
    int COUPON_ID;
    String COUPON_CODE;
    String COUPON_TITLE;
    int SCORE_MIN;
    String COUPON_TYPE;
    String COUPON_CATEGORY;
    Date VALID_DATE;
    Date EXPIRE_DATE;
    double MIN_ORDER_VALUE;
    double MAXIMUM_DISCOUNT;
    double COUPON_VALUE;
    int MAXIMUM_USERS;
    ArrayList<Integer> customerIds; // Thêm biến customerIds dạng mảng ArrayList
    private boolean isSelected;
    private boolean isValidVoucher;


    public VoucherCheckout(int COUPON_ID, String COUPON_CODE, String COUPON_TITLE, int SCORE_MIN, String COUPON_TYPE, String COUPON_CATEGORY, Date VALID_DATE, Date EXPIRE_DATE, double MIN_ORDER_VALUE, double MAXIMUM_DISCOUNT, double COUPON_VALUE, int MAXIMUM_USERS, ArrayList<Integer> customerIds, boolean isSelected, boolean isValidVoucher) {
        this.COUPON_ID = COUPON_ID;
        this.COUPON_CODE = COUPON_CODE;
        this.COUPON_TITLE = COUPON_TITLE;
        this.SCORE_MIN = SCORE_MIN;
        this.COUPON_TYPE = COUPON_TYPE;
        this.COUPON_CATEGORY = COUPON_CATEGORY;
        this.VALID_DATE = VALID_DATE;
        this.EXPIRE_DATE = EXPIRE_DATE;
        this.MIN_ORDER_VALUE = MIN_ORDER_VALUE;
        this.MAXIMUM_DISCOUNT = MAXIMUM_DISCOUNT;
        this.COUPON_VALUE = COUPON_VALUE;
        this.MAXIMUM_USERS = MAXIMUM_USERS;
        this.customerIds = customerIds;
        this.isSelected = isSelected;
        this.isValidVoucher = isValidVoucher;
    }

    public int getCOUPON_ID() {
        return COUPON_ID;
    }

    public void setCOUPON_ID(int COUPON_ID) {
        this.COUPON_ID = COUPON_ID;
    }

    public String getCOUPON_CODE() {
        return COUPON_CODE;
    }

    public void setCOUPON_CODE(String COUPON_CODE) {
        this.COUPON_CODE = COUPON_CODE;
    }

    public String getCOUPON_TITLE() {
        return COUPON_TITLE;
    }

    public void setCOUPON_TITLE(String COUPON_TITLE) {
        this.COUPON_TITLE = COUPON_TITLE;
    }

    public int getSCORE_MIN() {
        return SCORE_MIN;
    }

    public void setSCORE_MIN(int SCORE_MIN) {
        this.SCORE_MIN = SCORE_MIN;
    }

    public String getCOUPON_TYPE() {
        return COUPON_TYPE;
    }

    public void setCOUPON_TYPE(String COUPON_TYPE) {
        this.COUPON_TYPE = COUPON_TYPE;
    }

    public String getCOUPON_CATEGORY() {
        return COUPON_CATEGORY;
    }

    public void setCOUPON_CATEGORY(String COUPON_CATEGORY) {
        this.COUPON_CATEGORY = COUPON_CATEGORY;
    }

    public Date getVALID_DATE() {
        return VALID_DATE;
    }

    public void setVALID_DATE(Date VALID_DATE) {
        this.VALID_DATE = VALID_DATE;
    }

    public Date getEXPIRE_DATE() {
        return EXPIRE_DATE;
    }

    public void setEXPIRE_DATE(Date EXPIRE_DATE) {
        this.EXPIRE_DATE = EXPIRE_DATE;
    }

    public double getMIN_ORDER_VALUE() {
        return MIN_ORDER_VALUE;
    }

    public void setMIN_ORDER_VALUE(double MIN_ORDER_VALUE) {
        this.MIN_ORDER_VALUE = MIN_ORDER_VALUE;
    }

    public double getMAXIMUM_DISCOUNT() {
        return MAXIMUM_DISCOUNT;
    }

    public void setMAXIMUM_DISCOUNT(double MAXIMUM_DISCOUNT) {
        this.MAXIMUM_DISCOUNT = MAXIMUM_DISCOUNT;
    }

    public double getCOUPON_VALUE() {
        return COUPON_VALUE;
    }

    public void setCOUPON_VALUE(double COUPON_VALUE) {
        this.COUPON_VALUE = COUPON_VALUE;
    }

    public int getMAXIMUM_USERS() {
        return MAXIMUM_USERS;
    }

    public void setMAXIMUM_USERS(int MAXIMUM_USERS) {
        this.MAXIMUM_USERS = MAXIMUM_USERS;
    }

    public ArrayList<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(ArrayList<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isValidVoucher() {
        return isValidVoucher;
    }

    public void setValidVoucher(boolean validVoucher) {
        isValidVoucher = validVoucher;
    }
}
