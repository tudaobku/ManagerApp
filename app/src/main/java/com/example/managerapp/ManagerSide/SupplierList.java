package com.example.managerapp.ManagerSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.managerapp.Supplier;
import com.example.managerapp.R;
import com.example.managerapp.ManagerSide.Interface.ItemClickListener;
import com.example.managerapp.ManagerSide.ViewHolder.SupplierViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
        FirebaseRecyclerOptions<Supplier> options = new FirebaseRecyclerOptions.Builder<Supplier>()
                .setQuery(supplierList, Supplier.class).build();
        adapter = new FirebaseRecyclerAdapter<Supplier, SupplierViewHolder>(options) {
            @NonNull
            @Override
            public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_item, parent, false);
                return new SupplierViewHolder(itemView);
            }
            @Override
            protected void onBindViewHolder(@NonNull SupplierViewHolder supplierViewHolder, int i, @NonNull Supplier supplier) {
                supplierViewHolder.txtName.setText(supplier.getName());
                supplierViewHolder.txtId.setText(supplier.getSupplierID());
                if (!supplier.getImage().isEmpty()) {
                    Picasso.with(getBaseContext()).load(supplier.getImage()).into(supplierViewHolder.imgSupplier);
                }else {
                    supplierViewHolder.imgSupplier.setImageResource(R.drawable.no_image);
                }
                final Supplier local = supplier;
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

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}