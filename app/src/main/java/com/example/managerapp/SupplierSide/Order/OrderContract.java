package com.example.managerapp.SupplierSide.Order;

import com.example.managerapp.SupplierSide.Model.Order;

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
