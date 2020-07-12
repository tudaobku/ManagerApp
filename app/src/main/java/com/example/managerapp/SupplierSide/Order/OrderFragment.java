package com.example.managerapp.SupplierSide.Order;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.managerapp.SupplierSide.Model.Order;
import com.example.managerapp.R;

import java.util.ArrayList;

public class OrderFragment extends Fragment implements OrderContract.View, OrderAdapter.OrderListener {

    OrderContract.Presenter presenter;
    RecyclerView recyclerOrder;
    OrderAdapter orderAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.order_fragment, container, false);

        recyclerOrder = root.findViewById(R.id.recycler_order);
        recyclerOrder.setHasFixedSize(true);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter = new OrderPresenter(this);
        presenter.loadOrder();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.removeOrder(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(recyclerOrder);
        return root;

    }

    @Override
    public void onOrderCompleteClick(int position) {
        presenter.completeOrder(position);
    }

    @Override
    public void showOrder(ArrayList<Order> orderList) {
        orderAdapter = new OrderAdapter(orderList,this);
        recyclerOrder.setAdapter(orderAdapter);
    }

    @Override
    public void showConfirmDialog(String message, final int position, final String opt) {
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Order " + (position + 1) + ": " + message)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.loadOrder();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.confirmOperation(position, opt);
                    }
                })
                .setIcon(R.drawable.food)
                .create();
        alertDialog.show();
    }
}