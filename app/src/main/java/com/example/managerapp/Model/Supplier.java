package com.example.managerapp.Model;

public class Supplier {
    private String name;
    private String password;
    private String supplierID;

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

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Supplier() {
    }

    public Supplier(String name, String password, String supplierID) {
        this.name = name;
        this.password = password;
        this.supplierID = supplierID;
    }
}
