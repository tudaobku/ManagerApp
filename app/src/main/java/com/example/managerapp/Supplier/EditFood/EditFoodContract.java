package com.example.managerapp.Supplier.EditFood;

import android.net.Uri;

import com.example.managerapp.Supplier.Model.Food;
import com.example.managerapp.Supplier.Model.MenuItem;

public interface EditFoodContract {
    interface View {
        void showInvalidMessage(String message);
        void showFoodDetail(Food food);
        void showFoodImage(Uri uri);
        void showProgress(String message);
        void stopProgress();
        void showConnectionError();
        void closeView();
    }
    interface Presenter{
        void loadFoodDetail(int position);
        void saveFood(MenuItem menuItem);
        void saveLocalUri(Uri uri);
    }
    interface Interactor{
        Food getFood(int position);
        void performUpdateFood(int position, Food food);
        void performAddNewFood(Food food);
    }
}
