package com.example.managerapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.R;

public class OrderFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtName, txtQuantity;

    public OrderFoodViewHolder(View itemView){
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtQuantity = itemView.findViewById(R.id.txtQuantity);
    }

    @Override
    public void onClick(View view) {

    }
}
