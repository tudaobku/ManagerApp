package com.example.managerapp;

public class Supplier {
    private String name;
    private String password;
    private String supplierID;
    private String image;
    private String email;

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSupplierID() {
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

    public Supplier(String name, String password, String supplierID, String image, String email) {
        this.name = name;
        this.password = password;
        this.supplierID = supplierID;
        this.image = image;
        this.email = email;
    }
}
