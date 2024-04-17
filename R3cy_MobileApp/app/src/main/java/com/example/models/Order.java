package com.example.models;

public class Order {
    int OrderID;
    int OrderLineID;
    int OrderLineProductID;
    double OrderSalePrice;
    String Quantity;
    int OrderCusID;


    double TotalOrderValue;
    String OrderStatus;


    double TotalAmount;


    public byte[] getProductImg() {
        return productImg;
    }

    public void setProductImg(byte[] productImg) {
        this.productImg = productImg;
    }

    byte[] productImg;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    String productName;


    public Order(int orderID, int orderLineID, int orderLineProductID, double orderSalePrice, String quantity, int orderCusID, double totalOrderValue, String orderStatus, double totalAmount, byte[] productImg, String productName) {
        OrderID = orderID;
        OrderLineID = orderLineID;
        OrderLineProductID = orderLineProductID;
        OrderSalePrice = orderSalePrice;
        Quantity = quantity;
        OrderCusID = orderCusID;
        TotalOrderValue = totalOrderValue;
        OrderStatus = orderStatus;
        TotalAmount = totalAmount;
        this.productImg = productImg;
        this.productName = productName;
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

    public int getOrderLineProductID() {
        return OrderLineProductID;
    }

    public void setOrderLineProductID(int orderLineProductID) {
        OrderLineProductID = orderLineProductID;
    }

    public double getOrderSalePrice() {
        return OrderSalePrice;
    }

    public void setOrderSalePrice(double orderSalePrice) {
        OrderSalePrice = orderSalePrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getOrderCusID() {
        return OrderCusID;
    }

    public void setOrderCusID(int orderCusID) {
        OrderCusID = orderCusID;
    }

//    public String getOrderdate() {
//        return Orderdate;
//    }
//
//    public void setOrderdate(String orderdate) {
//        Orderdate = orderdate;
//    }
//
//    public String getPaymentmethod() {
//        return Paymentmethod;
//    }
//
//    public void setPaymentmethod(String paymentmethod) {
//        Paymentmethod = paymentmethod;
//    }
//
//    public int getPaymentID() {
//        return PaymentID;
//    }
//
//    public void setPaymentID(int paymentID) {
//        PaymentID = paymentID;
//    }
//
//    public int getCouponID() {
//        return CouponID;
//    }
//
//    public void setCouponID(int couponID) {
//        CouponID = couponID;
//    }

    public double getTotalOrderValue() {
        return TotalOrderValue;
    }

    public void setTotalOrderValue(double totalOrderValue) {
        TotalOrderValue = totalOrderValue;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

//    public String getOrderNote() {
//        return OrderNote;
//    }
//
//    public void setOrderNote(String orderNote) {
//        OrderNote = orderNote;
//    }
//
//    public String getDeliDate() {
//        return DeliDate;
//    }
//
//    public void setDeliDate(String deliDate) {
//        DeliDate = deliDate;
//    }
//
//    public String getDiscount() {
//        return Discount;
//    }
//
//    public void setDiscount(String discount) {
//        Discount = discount;
//    }
//
//    public double getShipping_fee() {
//        return Shipping_fee;
//    }
//
//    public void setShipping_fee(double shipping_fee) {
//        Shipping_fee = shipping_fee;
//    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }
}

//    public String getPaymentStatus() {
//        return PaymentStatus;
//    }
//
//    public void setPaymentStatus(String paymentStatus) {
//        PaymentStatus = paymentStatus;
//    }
//
//    public int getAddressID() {
//        return AddressID;
//    }
//
//    public void setAddressID(int addressID) {
//        AddressID = addressID;
//    }
//}
