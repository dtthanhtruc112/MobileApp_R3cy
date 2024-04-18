package com.example.models;

public class Address {
    int AddressId;
    int CustomerID;
    String ReceiverName;
    String ReceiverPhone;
    String Province;
    String District;
    String Ward;
    String AddressDetails;
    int IsDefault;
    String AddressType;

    private boolean isSelected;

    public Address(int addressId, int customerID, String receiverName, String receiverPhone, String province, String district, String ward, String addressDetails, int isDefault, String addressType, boolean isSelected) {
        AddressId = addressId;
        CustomerID = customerID;
        ReceiverName = receiverName;
        ReceiverPhone = receiverPhone;
        Province = province;
        District = district;
        Ward = ward;
        AddressDetails = addressDetails;
        IsDefault = isDefault;
        AddressType = addressType;
        this.isSelected = isSelected;
    }


    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        ReceiverPhone = receiverPhone;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getAddressDetails() {
        return AddressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        AddressDetails = addressDetails;
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int isDefault) {
        IsDefault = isDefault;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
