package com.example.managerapp.Supplier.Interface;

import com.example.managerapp.Supplier.Model.Order;

import java.util.ArrayList;

public interface OrderContract {
    interface Presenter{
        void loadOrder();
        void removeOrder(int position);
        void completeOrder(int position);
        void confirmOperation(int position, String opt);
    }
    interface View{
        void showOrder(ArrayList<Order> orderList);
        void showConfirmDialog(String message, int position, String opt);
    }
}
