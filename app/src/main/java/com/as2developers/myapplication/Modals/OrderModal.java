package com.as2developers.myapplication.Modals;

public class OrderModal {

    String items, Address , Date,PaymentMode;

    public OrderModal(String items, String address, String date, String paymentMode) {
        this.items = items;
        Address = address;
        Date = date;
        PaymentMode = paymentMode;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }
}
