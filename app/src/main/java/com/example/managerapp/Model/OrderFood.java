package com.example.managerapp.Model;

public class OrderFood {
    String name;
    String quantity;

    public OrderFood() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public OrderFood(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
