package com.example.managerapp.Supplier.Model;

public class Account {
    String phone;
    String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public Account() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
