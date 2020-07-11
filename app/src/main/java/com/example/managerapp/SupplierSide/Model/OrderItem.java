package com.example.managerapp.SupplierSide.Model;

public class OrderItem {
    String name;
    String quantity;

    public OrderItem() {
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

    public OrderItem(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
