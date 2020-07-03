package com.example.managerapp.Fragment;

import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.MatrixCursor;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.managerapp.Common;
import com.example.managerapp.FoodDetail;
import com.example.managerapp.Model.Food;
import com.example.managerapp.R;
import com.example.managerapp.UI.ItemClickListener;
import com.example.managerapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {


    RecyclerView recyclerMenu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapterMenu;

    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;

    DatabaseReference foodList;
    SimpleCursorAdapter mAdapter;
    List<String> foodNameList = new ArrayList<>();

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.menu_fragment, container, false);
        recyclerMenu = root.findViewById(R.id.recycler_menu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerMenu.setLayoutManager(layoutManager);
        foodList = FirebaseDatabase.getInstance().getReference("Food");
        loadMenu();
        return root;
    }

    private void loadMenu() {
        adapterMenu = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("supplierID").equalTo(Common.currentSupplier.getSupplierID())) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {

                foodViewHolder.txtName.setText(food.getName());
                Picasso.with(getContext()).load(food.getImage()).into(foodViewHolder.imgFood);
                foodNameList.add(food.getName());
                final Food clickItem = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent foodDetail = new Intent(getContext(), FoodDetail.class);
                        foodDetail.putExtra("foodID", adapterMenu.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        adapterMenu.notifyDataSetChanged();
        recyclerMenu.setAdapter(adapterMenu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Remove")) foodList.child(adapterMenu.getRef(item.getOrder()).getKey()).removeValue();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.food_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search with your food");

        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, foodNameList);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setDropDownBackgroundResource(R.color.white);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchAutoComplete.setText("" + adapterView.getItemAtPosition(i));
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

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showResult(String s) {
        if(s.isEmpty()) recyclerMenu.setAdapter(adapterMenu);
        else{
            searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
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
                            foodDetail.putExtra("foodID", searchAdapter.getRef(position).getKey());
                            startActivity(foodDetail);
                        }
                    });
                }
            };
            adapterMenu.notifyDataSetChanged();
            recyclerMenu.setAdapter(searchAdapter);
        }
    }


}