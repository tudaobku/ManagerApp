package com.example.managerapp;

public class Supplier {
    private String name;
    private String password;
    private Integer supplierID;
    private String image;

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Supplier() {
    }

    public Supplier(String name, String password, Integer supplierID, String image) {
        this.name = name;
        this.password = password;
        this.supplierID = supplierID;
        this.image = image;
    }
}
