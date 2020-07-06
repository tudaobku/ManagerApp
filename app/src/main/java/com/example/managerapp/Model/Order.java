package com.example.managerapp.Model;

import java.util.List;

public class Order {
    List<OrderFood> foods;
    String phone;
    String status;
    String total;

    public Order() {
    }

    public List<OrderFood> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderFood> foods) {
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

    public Order(List<OrderFood> foods, String phone, String status, String total) {
        this.foods = foods;
        this.phone = phone;
        this.status = status;
        this.total = total;
    }
}
