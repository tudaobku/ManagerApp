package com.example.managerapp.Model;

public class Food {
    String description;
    String discount;
    String image;
    String name;
    String price;
    String supplierID;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Food() {
    }

    public Food(String description, String discount, String image, String name, String price, String supplierID) {
        this.description = description;
        this.discount = discount;
        this.image = image;
        this.name = name;
        this.price = price;
        this.supplierID = supplierID;
    }
}
