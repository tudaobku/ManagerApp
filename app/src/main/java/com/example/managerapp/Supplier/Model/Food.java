package com.example.managerapp.Supplier.Model;

public class Food extends MenuItem{
    String image;
    String supplierID;
    String foodID;
    String status;
    String rating;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Food(String image, String supplierID, String foodID, String status, String rating) {
        this.image = image;
        this.supplierID = supplierID;
        this.foodID = foodID;
        this.status = status;
        this.rating = rating;
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
