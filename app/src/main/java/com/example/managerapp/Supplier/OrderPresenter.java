package com.example.managerapp.Supplier;

import androidx.annotation.NonNull;

import com.example.managerapp.Supplier.Interface.OrderContract;
import com.example.managerapp.Supplier.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderPresenter implements OrderContract.Presenter{
    OrderContract.View orderView;
    DatabaseReference orderReference;
    ArrayList<Order> orderList;
    ArrayList<String> keyList;
    OrderPresenter (OrderContract.View orderView){
        this.orderView = orderView;
        orderList = new ArrayList<>();
        orderReference = FirebaseDatabase.getInstance().getReference("Order/CurrentOrder/List");
    }

    @Override
    public void loadOrder() {
        orderReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }
}
