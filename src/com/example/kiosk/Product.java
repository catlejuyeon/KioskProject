package com.example.kiosk;

public interface Product {
    int getId();
    String getName();
    int getPrice();
    String getDescription();
    void display();
    int getQuantity();
    void setQuantity(int quantity);
}
