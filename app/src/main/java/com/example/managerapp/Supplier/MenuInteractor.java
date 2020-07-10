package com.example.managerapp.Supplier;

import android.view.Menu;

import androidx.annotation.NonNull;

import com.example.managerapp.Supplier.Interface.EditFoodContract;
import com.example.managerapp.Supplier.Interface.MenuContract;
import com.example.managerapp.Supplier.Model.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MenuInteractor implements MenuContract.Interactor, EditFoodContract.Interactor {
    static MenuInteractor menuInteractor;
    ArrayList<Food> foodList;
    ArrayList<String> keyList;
    MenuContract.onOperationListener menuListener;
    DatabaseReference foodReference;

    private MenuInteractor(MenuContract.onOperationListener menuListener) {
        foodList = new ArrayList<>();
        keyList = new ArrayList<>();
        foodReference = FirebaseDatabase.getInstance().getReference("Food/List");
        this.menuListener = menuListener;
    }

    static public MenuInteractor getInstance(){
        return menuInteractor;
    }

    static public MenuInteractor getInstance(MenuContract.onOperationListener menuListener){
        menuInteractor = new MenuInteractor(menuListener);
        return menuInteractor;
    }

    @Override
    public void performLoadMenu(final String supplierID) {
        foodReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot foodSnapshot: snapshot.getChildren()){
                    Food food = foodSnapshot.getValue(Food.class);
                    assert food != null;
                    if(food.getSupplierID().equals(supplierID)){
                        foodList.add(food);
                        keyList.add(foodSnapshot.getKey());
                    }
                }
                menuListener.onLoadMenu(foodList, true);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public Food getFood(int position) {
        return foodList.get(position);
    }

    @Override
    public void performUpdateFood(final int position, final Food food) {
        String key = keyList.get(position);
        foodReference.child(key).setValue(food).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                boolean change = true;
                if(foodList.get(position).getName().equals(food.getName())) change = false;
                foodList.set(position, food);
                menuListener.onLoadMenu(foodList, change);
            }
        });
    }

    @Override
    public void performAddNewFood(final Food food) {
        final String key = foodReference.push().getKey();
        foodReference.child(key).setValue(food).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    foodList.add(food);
                    keyList.add(key);
                    menuListener.onLoadMenu(foodList, true);
                    menuListener.onAddSuccess();
                }
                else{
                    menuListener.onAddFailure();
                }
            }
        });
    }

    @Override
    public void performRemoveFood(final int position) {
        String key = keyList.get(position);
        foodReference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                foodList.remove(position);
                keyList.remove(position);
                menuListener.onLoadMenu(foodList, true);
            }
        });
    }

    @Override
    public void performSearchFoods(String key) {
        ArrayList<Food> searchList = new ArrayList<>();
        for(Food food: foodList){
            if(food.getName().contains(key)) searchList.add(food);
        }
        menuListener.onLoadMenu(searchList, false);
    }

    @Override
    public void performChangeStatus(final int position) {
        String key = keyList.get(position);
        final String status;
        if(foodList.get(position).getStatus().equals("0")) status = "1";
        else status = "0";
        foodReference.child(key).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                foodList.get(position).setStatus(status);
                menuListener.onLoadMenu(foodList, false);
            }
        });
    }

    @Override
    public int getMenuSize() {
        return foodList.size();
    }

}
