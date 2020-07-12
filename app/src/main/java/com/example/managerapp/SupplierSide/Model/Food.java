package com.example.managerapp.SupplierSide.Model;

public class Food extends MenuItem{
    String image;
    String supplierID;
    String foodID;
    String status;
    String star;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public Food(String image, String supplierID, String foodID, String status, String star) {
        this.image = image;
        this.supplierID = supplierID;
        this.foodID = foodID;
        this.status = status;
        this.star = star;
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
