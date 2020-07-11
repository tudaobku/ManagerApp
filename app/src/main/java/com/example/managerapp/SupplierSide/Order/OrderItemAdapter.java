package com.example.managerapp.SupplierSide.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.SupplierSide.Model.OrderItem;
import com.example.managerapp.R;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
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

    class OrderItemViewHolder extends RecyclerView.ViewHolder  {
        TextView txtName, txtQuantity;
        OrderItemViewHolder(View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }

}
