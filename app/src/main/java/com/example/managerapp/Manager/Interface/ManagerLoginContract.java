package com.example.managerapp.Manager.Interface;

public interface ManagerLoginContract {
    interface View {
        void showToast(String message);
        void startHomePage();
        void showWaitingDialog();
        void closeWaitingDialog();
    }

    interface Presenter {
        void login(String phone, String password);
        void forgotPassword();
    }
}
