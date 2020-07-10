package com.example.managerapp.Supplier.Interface;

public interface SupplierLoginContract {
    interface View{
        void showToast(String message);
        void startHomePage();
        void showWaitingDialog();
        void closeWaitingDialog();
    }
    interface Presenter{
        void login(String phone, String password);
        void forgotPassword();
    }
}
