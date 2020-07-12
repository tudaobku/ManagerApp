package com.example.managerapp.SupplierSide.Menu;

import com.example.managerapp.SupplierSide.Model.Food;

import java.util.ArrayList;

public interface MenuContract {
    interface Presenter{
        void loadMenu();
        void removeFood(int position);
        void changeFoodStatus(int position);
        void loadSearchFoods(String key);
        void requestAddFood();
    }
    interface View {
        void showLimitExcessDialog();
        void showAddFood();
        void showToast(String message);
        void showMenu(ArrayList<Food> menu);
        void showConnectionError();
        void setSuggestionList(ArrayList<String> foodNameList);
        void onProcessStart();
        void onProcessEnd();
    }
    interface Interactor{
        void performLoadMenu(String supplierID);
        void performRemoveFood(int position);
        void performSearchFoods(String key);
        void performChangeStatus(int position);
        int getMenuSize();
    }
    interface onOperationListener{
        void onAddSuccess();
        void onAddFailure();
        void onLoadMenu(ArrayList<Food> foodList, boolean change);
    }
}
