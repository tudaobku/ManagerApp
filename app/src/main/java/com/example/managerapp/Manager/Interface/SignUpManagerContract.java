package com.example.managerapp.Manager.Interface;

public interface SignUpManagerContract {
    interface View {
        void showToast(String message);
        void startHomePage(String name, String email, String password, String phone);
        void showWaitingDialog();
        void closeWaitingDialog();
    }

    interface Presenter {
        void registerUser(String name, String email, String phone, String password);
    }
}
