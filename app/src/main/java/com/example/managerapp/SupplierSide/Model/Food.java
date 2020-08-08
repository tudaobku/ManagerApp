package com.example.managerapp.SupplierSide.Model;

public class Food extends MenuItem{
    String image;
    Integer supplierID;
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

    public Food(String image, Integer supplierID, String status, String star) {
        this.image = image;
        this.supplierID = supplierID;
        this.status = status;
        this.star = star;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
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

}
