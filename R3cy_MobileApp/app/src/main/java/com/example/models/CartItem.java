package com.example.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CartItem implements Serializable {
    int ProductId;
    ;
    String productName;
    String productCategory;
    double productPrice;
    int productQuantity;
    byte[] productThumb;
    private boolean isSelected;
    int lineId;
    public CartItem(int productId, String productName, String productCategory, double productPrice, int productQuantity, byte[] productThumb, boolean isSelected, int lineId) {
        ProductId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productThumb = productThumb;
        this.isSelected = isSelected;
        this.lineId = lineId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public byte[] getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(byte[] productThumb) {
        this.productThumb = productThumb;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }


}