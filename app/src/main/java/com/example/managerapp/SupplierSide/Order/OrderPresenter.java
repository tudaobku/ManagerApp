package com.example.managerapp.SupplierSide.Order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.managerapp.SupplierSide.Common;
import com.example.managerapp.SupplierSide.Model.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderPresenter implements OrderContract.Presenter{
    OrderContract.View orderView;
    DatabaseReference orderReference;
    ArrayList<Order> orderList;
    ArrayList<String> keyList;
    public OrderPresenter (OrderContract.View orderView){
        this.orderView = orderView;

        orderReference = FirebaseDatabase.getInstance().getReference("Order/CurrentOrder/List");
    }

    @Override
    public void loadOrder() {
        orderList = new ArrayList<>();
        keyList = new ArrayList<>();
        orderReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if(order.getSupplierID().equals(Common.supplier.getSupplierID())){
                    orderList.add(order);
                    keyList.add(snapshot.getKey());
                    orderView.showOrder(orderList);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if(order.getSupplierID().equals(Common.supplier.getSupplierID())) {
                    int position = keyList.indexOf(snapshot.getKey());
                    orderList.set(position, order);
                    orderView.showOrder(orderList);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Order order = snapshot.getValue(Order.class);
                if(order.getSupplierID().equals(Common.supplier.getSupplierID())) {
                    int position = keyList.indexOf(snapshot.getKey());
                    orderList.remove(position);
                    keyList.remove(position);
                    orderView.showOrder(orderList);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void removeOrder(int position) {
        orderView.showConfirmDialog("Remove Order. Sure?", position, Common.REMOVE_OPT);
    }

    @Override
    public void completeOrder(int position) {
        Order order = orderList.get(position);
        if(order.getStatus().equals("0")) orderView.showConfirmDialog("Order is ready?", position, Common.UPDATE_OPT);
    }

    @Override
    public void confirmOperation(int position, String opt) {
        if(opt == Common.UPDATE_OPT) orderReference.child(keyList.get(position)).child("status").setValue("1");
        else orderReference.child(keyList.get(position)).removeValue();
    }

}
