package com.example.managerapp.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.managerapp.Model.Supplier;
import com.example.managerapp.R;
import com.example.managerapp.UI.ItemClickListener;
import com.example.managerapp.ViewHolder.SupplierViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SupplierList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference supplierList;

    FirebaseRecyclerAdapter<Supplier, SupplierViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        supplierList = database.getReference("Supplier/List");

        recyclerView = findViewById(R.id.recycler_supplier);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadSupplierList();
    }

    private void loadSupplierList() {
        adapter = new FirebaseRecyclerAdapter<Supplier, SupplierViewHolder>(Supplier.class,
                R.layout.supplier_item,
                SupplierViewHolder.class,
                supplierList) {
            @Override
            protected void populateViewHolder(SupplierViewHolder supplierViewHolder, Supplier model, int i) {
                supplierViewHolder.txtName.setText(model.getName());
                supplierViewHolder.txtId.setText(model.getSupplierID());
                if (!model.getImage().isEmpty()) {
                    Picasso.with(getBaseContext()).load(model.getImage()).into(supplierViewHolder.imgSupplier);
                }else {
                    supplierViewHolder.imgSupplier.setImageResource(R.drawable.no_image);
                }
                final Supplier local = model;
                supplierViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Remove")) adapter.getRef(item.getOrder()).removeValue();
        return super.onContextItemSelected(item);
    }
}