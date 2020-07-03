package com.example.managerapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder  {
    public TextView txtName, txtQuantity;
    public OrderItemViewHolder(View itemView){
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtQuantity = itemView.findViewById(R.id.txtQuantity);
    }
}
