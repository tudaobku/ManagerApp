package com.example.managerapp.SupplierSide.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managerapp.R;
import com.example.managerapp.SupplierSide.Model.Order;

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
        Order order = orderList.get(position);
        holder.txtPhone.setText(order.getPhone());
        holder.txtNumber.setText(String.valueOf(position + 1));
        holder.recyclerOrderItem.setLayoutManager(new LinearLayoutManager(orderListener.getContext()));
        holder.recyclerOrderItem.setAdapter(new OrderItemAdapter(order.getFoods() , orderListener.getContext()));
        if(order.getStatus().equals("0")){
            holder.txtStatus.setText("Preparing");
            holder.txtStatus.setBackgroundColor(0xFFC52F2F);
        }
        else{
            holder.txtStatus.setText("Completed");
            holder.txtStatus.setBackgroundColor(0xFF72DA76);
        }
        if (order.getType().equals("0")) holder.txtTakeAway.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OrderListener orderListener;
        public TextView txtPhone, txtStatus, txtNumber, txtTakeAway;
        public RecyclerView recyclerOrderItem;

        public OrderViewHolder(View itemView, OrderListener orderListener) {
            super(itemView);
            this.orderListener = orderListener;
            txtPhone = itemView.findViewById(R.id.txtPhone);
            recyclerOrderItem = itemView.findViewById(R.id.orderList);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtTakeAway = itemView.findViewById(R.id.txtTakeAway);
            txtStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.txtStatus) orderListener.onOrderCompleteClick(getAdapterPosition());
        }
    }
    public interface OrderListener {
        void onOrderCompleteClick(int position);
        Context getContext();
    }

}
