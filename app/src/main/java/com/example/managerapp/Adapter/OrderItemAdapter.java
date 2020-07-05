package com.example.managerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.Model.OrderItem;
import com.example.managerapp.R;
import com.example.managerapp.ViewHolder.OrderItemViewHolder;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    List<OrderItem> foodList;
    Context context;

    public OrderItemAdapter(List<OrderItem> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item_item,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        holder.txtName.setText(foodList.get(position).getName());
        holder.txtQuantity.setText(foodList.get(position).getQuantity());
    }
    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
