package com.example.managerapp.SupplierSide.Menu;

import com.example.managerapp.SupplierSide.Common;
import com.example.managerapp.SupplierSide.Model.Food;


import java.util.ArrayList;

public class MenuPresenter implements MenuContract.Presenter, MenuContract.onOperationListener {
    MenuContract.View menuView;
    MenuInteractor menuInteractor;

    public MenuPresenter(MenuContract.View menuView) {
        this.menuView = menuView;
        menuInteractor = MenuInteractor.getInstance(this);
    }

    @Override
    public void loadMenu() {
        menuView.onProcessStart();
        menuInteractor.performLoadMenu(Common.supplier.getSupplierID());
    }

    @Override
    public void removeFood(int position) {
        menuView.onProcessStart();
        menuInteractor.performRemoveFood(position);
    }

    @Override
    public void changeFoodStatus(int position) {
        menuInteractor.performChangeStatus(position);
    }


    @Override
    public void loadSearchFoods(String key) {
        menuInteractor.performSearchFoods(key);
    }

    @Override
    public void requestAddFood() {
        if(menuInteractor.getMenuSize() >= 50) menuView.showLimitExcessDialog();
        else menuView.showAddFood();
    }

    @Override
    public void onAddSuccess() {
        menuView.showToast("Food added to your menu");
    }

    @Override
    public void onAddFailure() {
        menuView.showConnectionError();
    }

    @Override
    public void onLoadMenu(ArrayList<Food> foodList, boolean change) {
        menuView.showMenu(foodList);
        if(change){
            ArrayList<String> foodNameList = new ArrayList<>();
            for(Food food: foodList){
                foodNameList.add(food.getName());
            }
            menuView.setSuggestionList(foodNameList);
        }
        menuView.onProcessEnd();
    }
}
