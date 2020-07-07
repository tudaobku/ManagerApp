package com.example.managerapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.R;
import com.example.managerapp.UI.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    public TextView txtPhone, txtStatus;
    public RecyclerView recyclerOrderItem;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView){
        super(itemView);

        txtPhone = itemView.findViewById(R.id.txtPhone);
        recyclerOrderItem = itemView.findViewById(R.id.orderList);
        txtStatus = itemView.findViewById(R.id.txtStatus);

        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}
