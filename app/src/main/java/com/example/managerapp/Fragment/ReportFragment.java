package com.example.managerapp.Fragment;

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

import com.example.managerapp.Model.Order;
import com.example.managerapp.R;
import com.example.managerapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Locale;

public class ReportFragment extends Fragment {


    RecyclerView recyclerReport;
    DatabaseReference reportList;
    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapterOrder;

    FirebaseDatabase database;
    DatabaseReference reports;

    TextView txtQuantity, txtTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.report_fragment, container, false);

        database = FirebaseDatabase.getInstance();
        reports = database.getReference("ConfirmedOrder");

        recyclerReport = root.findViewById(R.id.recycler_report);
        recyclerReport.setHasFixedSize(true);
        recyclerReport.setLayoutManager(new LinearLayoutManager(getContext()));
        reportList = FirebaseDatabase.getInstance().getReference("ConfirmedOrder");


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}