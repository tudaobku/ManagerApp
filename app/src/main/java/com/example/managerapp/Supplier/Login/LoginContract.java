package com.example.managerapp.Supplier.Login;

public interface LoginContract {
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
