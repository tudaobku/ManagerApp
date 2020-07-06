package com.example.managerapp.Supplier.Fragment;

import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.managerapp.Common;
import com.example.managerapp.Supplier.FoodDetail;
import com.example.managerapp.Model.Food;
import com.example.managerapp.R;
import com.example.managerapp.UI.ItemClickListener;
import com.example.managerapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {


    RecyclerView recyclerMenu;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapterFood;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapterSearchFood;
    DatabaseReference foodList;
    List<String> foodNameList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.food_fragment, container, false);
        recyclerMenu = root.findViewById(R.id.recycler_menu);
        recyclerMenu.setHasFixedSize(true);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        foodList = FirebaseDatabase.getInstance().getReference("Food/List");
        loadMenu();
        return root;
    }

    private void loadMenu() {
        adapterFood = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("supplierID").equalTo(Common.supplier.getSupplierID())) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.txtName.setText(food.getName());
                Picasso.with(getContext()).load(food.getImage()).into(foodViewHolder.imgFood);
                foodNameList.add(food.getName());

                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent foodDetail = new Intent(getContext(), FoodDetail.class);
                        foodDetail.putExtra("foodRef", adapterFood.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        adapterFood.notifyDataSetChanged();
        recyclerMenu.setAdapter(adapterFood);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Remove")) adapterFood.getRef(item.getOrder()).removeValue();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.food_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
   searchView.setQueryHint("Search with your food");

        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, foodNameList);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setDropDownBackgroundResource(R.color.white);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchAutoComplete.setText(adapterView.getItemAtPosition(i).toString());
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                showResult(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerMenu.setAdapter(adapterFood);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showResult(String s) {
        adapterSearchFood = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("name").equalTo(s)) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.txtName.setText(food.getName());
                Picasso.with(getContext()).load(food.getImage()).into(foodViewHolder.imgFood);

                final Food clickItem = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent foodDetail = new Intent(getContext(), FoodDetail.class);
                        foodDetail.putExtra("foodID", adapterSearchFood.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        adapterFood.notifyDataSetChanged();
        recyclerMenu.setAdapter(adapterSearchFood);
    }


}