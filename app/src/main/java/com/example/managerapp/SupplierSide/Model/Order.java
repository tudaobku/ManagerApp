package com.example.managerapp.SupplierSide.Model;

import java.util.List;

public class Order {
    List<OrderItem> foods;
    String phone;
    String status;
    Integer supplierID;
    String total;
    String type;

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Order() {
    }

    public List<OrderItem> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderItem> foods) {
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Order(List<OrderItem> foods, String phone, String status, String total, String type, Integer supplierID) {
        this.foods = foods;
        this.phone = phone;
        this.status = status;
        this.total = total;
        this.type = type;
        this.supplierID = supplierID;
    }
}
