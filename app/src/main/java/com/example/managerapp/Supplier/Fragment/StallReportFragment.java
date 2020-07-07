package com.example.managerapp.Supplier.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.managerapp.Common;
import com.example.managerapp.Model.Order;
import com.example.managerapp.R;
import com.example.managerapp.UI.ItemClickListener;
import com.example.managerapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Locale;

public class StallReportFragment extends Fragment {


    RecyclerView recyclerReport;
    DatabaseReference reportList;
    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapterDeliveredOrder;

    FirebaseDatabase database;
    DatabaseReference reports;

    TextView txtQuantity, txtTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.stall_report_fragment, container, false);

        database = FirebaseDatabase.getInstance();
        reports = database.getReference("Order/DeliveredOrder");

        recyclerReport = root.findViewById(R.id.recycler_report);
        recyclerReport.setHasFixedSize(true);
        recyclerReport.setLayoutManager(new LinearLayoutManager(getContext()));
        reportList = FirebaseDatabase.getInstance().getReference("ConfirmedOrder");

        loadReport();

        return root;
    }

    private void loadReport() {
        adapterDeliveredOrder = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.activity_report_layout,
                OrderViewHolder.class,
                reportList.orderByChild("supplierID").equalTo(Common.supplier.getSupplierID())) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, final Order order, int i) {

                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }
                });
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}