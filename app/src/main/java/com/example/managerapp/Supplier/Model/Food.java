package com.example.managerapp.Supplier.Model;

public class Food extends MenuItem{
    String image;
    String supplierID;
    String foodID;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Food(String description, String discount, String image, String name, String price, String supplierID, String foodID, String status) {
        this.description = description;
        this.discount = discount;
        this.image = image;
        this.name = name;
        this.price = price;
        this.supplierID = supplierID;
        this.foodID = foodID;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }
    public void setMenuItem(MenuItem menuItem){
        name = menuItem.getName();
        description = menuItem.getDescription();
        discount = menuItem.getDiscount();
        price = menuItem.getPrice();
    }
    public Food() {
    }
    public String getFoodID() {
        return foodID;
    }
}
