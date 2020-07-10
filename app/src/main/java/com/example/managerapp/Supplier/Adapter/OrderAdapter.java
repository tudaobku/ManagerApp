package com.example.managerapp.Supplier.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.R;
import com.example.managerapp.Supplier.Common;
import com.example.managerapp.Supplier.Model.Food;
import com.example.managerapp.Supplier.Model.Order;
import com.example.managerapp.UI.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private ArrayList<Order> orderList;
    private OrderListener orderListener;

    public OrderAdapter(ArrayList<Order> orderList, OrderListener orderListener) {
        this.orderList = orderList;
        this.orderListener = orderListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(itemView, orderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.txtPhone.setText(orderList.get(position).getPhone());
        String status = orderList.get(position).getStatus();
        if (status.equals("0")) holder.txtStatus.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OrderListener orderListener;
        public TextView txtPhone, txtStatus;
        public RecyclerView recyclerOrderItem;

        public OrderViewHolder(View itemView, OrderListener orderListener) {
            super(itemView);
            this.orderListener = orderListener;
            txtPhone = itemView.findViewById(R.id.txtPhone);
            recyclerOrderItem = itemView.findViewById(R.id.orderList);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.txtStatus) orderListener.onStatusChangeClick(getAdapterPosition());
        }
    }
    public interface OrderListener {
        void onStatusChangeClick(int position);
    }

}
