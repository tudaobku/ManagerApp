package com.example.managerapp.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.managerapp.Adapter.OrderFoodAdapter;
import com.example.managerapp.Common;
import com.example.managerapp.Model.Order;
import com.example.managerapp.Model.OrderFood;
import com.example.managerapp.NewFood;
import com.example.managerapp.R;
import com.example.managerapp.Service.CommingOrder;
import com.example.managerapp.UI.ItemClickListener;
import com.example.managerapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class OrderFragment extends Fragment {


    RecyclerView recyclerOrder;
    DatabaseReference orderList;
    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapterOrder;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.order_fragment, container, false);

        recyclerOrder = root.findViewById(R.id.recycler_order);
        recyclerOrder.setHasFixedSize(true);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList = FirebaseDatabase.getInstance().getReference("Order");
        loadOrder();

/*
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    adapterOrder.getRef(position).child("status").setValue("3");
                    adapterOrder.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(recyclerOrder);*/

        return root;

    }

    private void loadOrder() {
       adapterOrder = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.order_item,
                OrderViewHolder.class,
                orderList.orderByChild("supplierID").equalTo(Common.currentSupplier.getSupplierID())) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, final Order order, int i) {

                orderViewHolder.txtPhone.setText(order.getPhone());
                if(order.getStatus().equals("0")){
                    orderViewHolder.txtStatus.setText("Waiting....");
                    orderViewHolder.txtStatus.setBackgroundColor(0xFFC52F2F);
                }
                else{
                    orderViewHolder.txtStatus.setText("Ready....");
                    orderViewHolder.txtStatus.setBackgroundColor(0xFF72DA76);
                }

                orderViewHolder.recyclerOrderFood.setLayoutManager(new LinearLayoutManager(getContext()));
                List<OrderFood> t = order.getFoods();
                orderViewHolder.recyclerOrderFood.setAdapter(new OrderFoodAdapter(t , getContext()));

                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        if(order.getStatus().equals("0")){
                            final AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
                            alertDialog.setTitle("Food is ready?")
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            adapterOrder.getRef(position).child("status").setValue("1");
                                        }
                                    })
                                    .setIcon(R.drawable.food)
                                    .create();
                            alertDialog.show();
                        }
                    }
                });
            }
        };
        adapterOrder.notifyDataSetChanged();
        recyclerOrder.setAdapter(adapterOrder);
    }
}