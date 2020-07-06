package com.example.managerapp.Model;

import java.util.List;

public class Report {

    private String supplierID;
    private List<OrderItem> foods;
    private String total;


    public Report(String total, String supplierID, List<OrderItem> foods) {
        this.total = total;
        this.supplierID = supplierID;
        this.foods = foods;
    }
    public Report(){}

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public List<OrderItem> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderItem> foods) {
        this.foods = foods;
    }


}

