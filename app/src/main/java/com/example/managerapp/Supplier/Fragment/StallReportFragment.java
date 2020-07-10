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

import com.example.managerapp.Supplier.Common;
import com.example.managerapp.Supplier.Model.Order;
import com.example.managerapp.R;
import com.example.managerapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        reports = database.getReference("Order");

        recyclerReport = root.findViewById(R.id.recycler_report);
        recyclerReport.setHasFixedSize(true);
        recyclerReport.setLayoutManager(new LinearLayoutManager(getContext()));
        reportList = FirebaseDatabase.getInstance().getReference("ConfirmedOrder");

        loadReport();

        return root;
    }

    private void loadReport() {
        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(reports.orderByChild("supplierID").equalTo(Common.supplier.getSupplierID()), Order.class).build();
        adapterDeliveredOrder = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull Order order) {

            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}