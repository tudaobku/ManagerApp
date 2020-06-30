package com.example.managerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.Model.Food;
import com.example.managerapp.Model.OrderFood;
import com.example.managerapp.R;
import com.example.managerapp.ViewHolder.FoodViewHolder;
import com.example.managerapp.ViewHolder.OrderFoodViewHolder;
import com.example.managerapp.ViewHolder.OrderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class OrderFoodAdapter extends RecyclerView.Adapter<OrderFoodViewHolder> {
    List<OrderFood> foodList = new ArrayList<>();
    Context context;

    public OrderFoodAdapter(List<OrderFood> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderFoodViewHolder(LayoutInflater.from(context).inflate(R.layout.order_food_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFoodViewHolder holder, int position) {
        holder.txtName.setText(foodList.get(position).getName());
        holder.txtQuantity.setText(foodList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
