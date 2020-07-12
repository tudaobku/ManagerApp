package com.example.managerapp.SupplierSide.Menu;

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

import com.example.managerapp.SupplierSide.Model.Food;
import com.example.managerapp.R;
import com.example.managerapp.SupplierSide.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.FoodViewHolder> {

    private ArrayList<Food> foodList;
    private FoodListener foodListener;
    public MenuAdapter(ArrayList<Food> foodList, FoodListener foodListener) {
        this.foodList = foodList;
        this.foodListener = foodListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(itemView, foodListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.txtName.setText(foodList.get(position).getName());
        Picasso.with(foodListener.getContext()).load(foodList.get(position).getImage()).into(holder.imgFood);
        String status = foodList.get(position).getStatus();
        if(status != null && status.equals("1")) holder.btnStatus.setBackgroundResource(R.drawable.outoforder);
        else holder.btnStatus.setBackgroundResource(R.drawable.serving);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        private FoodListener foodListener;
        private TextView txtName;
        private ImageView imgFood;
        private Button btnStatus;
        public FoodViewHolder(@NonNull View itemView, FoodListener foodListener) {
            super(itemView);
            this.foodListener = foodListener;
            txtName = itemView.findViewById(R.id.txtName);
            imgFood = itemView.findViewById(R.id.imgFood);
            btnStatus = itemView.findViewById(R.id.btnStatus);
            btnStatus.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btnStatus) foodListener.onStatusChangeClick(getAdapterPosition());
        }
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0,0,getAdapterPosition(), Common.REMOVE_OPT);
            contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE_OPT);
        }
    }
    public interface FoodListener {
        void onStatusChangeClick(int position);
        Context getContext();
    }
}
