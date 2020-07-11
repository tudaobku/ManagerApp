package com.example.managerapp.SupplierSide.Menu;

import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.managerapp.SupplierSide.Common;
import com.example.managerapp.SupplierSide.EditFood.EditFoodActivity;
import com.example.managerapp.SupplierSide.Model.Food;
import com.example.managerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements MenuContract.View, MenuAdapter.FoodListener{
    MenuContract.Presenter presenter;
    RecyclerView recyclerMenu;
    ProgressBar progressBar;
    MenuAdapter menuAdapter;
    SearchView searchView;
    FloatingActionButton btnAddFood;
    ArrayAdapter<String> foodNameAdapter;
    SearchView.SearchAutoComplete searchAutoComplete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.menu_fragment, container, false);

        btnAddFood = root.findViewById(R.id.btnAddFood);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerMenu = root.findViewById(R.id.recycler_menu);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerMenu.setHasFixedSize(true);
        recyclerMenu.setLayoutManager(linearLayoutManager);
        presenter = new MenuPresenter(this);
        presenter.loadMenu();

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestAddFood();
            }
        });
        return root;
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        if(item.getTitle().equals(Common.REMOVE_OPT)) {
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
            alertDialog.setTitle("Do you want to remove this food?")
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
                            presenter.removeFood(item.getOrder());
                        }
                    })
                    .setIcon(R.drawable.warning)
                    .create();
            alertDialog.show();

        }
        if(item.getTitle().equals(Common.UPDATE_OPT)) {
            Intent editIntent = new Intent(getContext(), EditFoodActivity.class);
            editIntent.putExtra(Common.EXTRA_FOOD_POSITION, item.getOrder());
            startActivity(editIntent);
        };
        return super.onContextItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.food_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView)searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search with your food");
        searchAutoComplete = searchView.findViewById(R.id.search_src_text);
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
                presenter.loadSearchFoods(s);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()) presenter.loadSearchFoods(s);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showMenu(ArrayList<Food> menu) {
        menuAdapter = new MenuAdapter(menu, this);
        recyclerMenu.setAdapter(menuAdapter);
    }

    @Override
    public void showConnectionError() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Connection Error")
                .setMessage("Please check your connection")
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(R.drawable.warning)
                .create();
        alertDialog.show();
    }

    @Override
    public void setSuggestionList(ArrayList<String> foodNameList) {
        foodNameAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, foodNameList);
        searchAutoComplete.setAdapter(foodNameAdapter);
    }

    @Override
    public void showLimitExcessDialog() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Warning")
                .setMessage("Maximum 50 items are allowed")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(R.drawable.food)
                .create();
        alertDialog.show();
    }

    @Override
    public void showAddFood() {
        Intent editIntent = new Intent(getContext(), EditFoodActivity.class);
        startActivity(editIntent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProcessEnd() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onStatusChangeClick(int position) {
        presenter.changeFoodStatus(position);
    }
}