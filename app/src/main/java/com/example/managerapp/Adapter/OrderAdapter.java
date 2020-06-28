package com.example.managerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.Model.Order;
import com.example.managerapp.R;
import com.example.managerapp.ViewHolder.OrderViewHolder;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    Context context;
    ArrayList<Order> orderList;

    public OrderAdapter(Context c, ArrayList<Order> p){
        context = c;
        orderList = p;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }



}
