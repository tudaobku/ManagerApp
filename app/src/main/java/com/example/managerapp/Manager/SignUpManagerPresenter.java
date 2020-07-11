package com.example.managerapp.Manager;

import android.content.Intent;
import android.util.Patterns;

import com.example.managerapp.Manager.Interface.SignUpManagerContract;
import com.example.managerapp.R;
import com.example.managerapp.Supplier.Interface.SupplierLoginContract;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpManagerPresenter implements SignUpManagerContract.Presenter {

    SignUpManagerContract.View mISignUp;
    public SignUpManagerPresenter(SignUpManagerContract.View iLoginCallBack) {
        mISignUp = iLoginCallBack;
    }

    @Override
    public void registerUser(String name, String email, String phone, String password) {

        if (checkInput(name, email, phone, password)) {
            mISignUp.showWaitingDialog();
            mISignUp.startHomePage(name, email, password, phone);
        }
    }

    private boolean checkInput(String name, String email, String phone, String password) {
        if (name.isEmpty()) {
            mISignUp.showToast("Please enter your name");
            return false;
        }

        if (email.isEmpty()) {
            mISignUp.showToast("Please enter your email address");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mISignUp.showToast("Invalid email address");
            return false;
        }

        if (password.isEmpty()) {
            mISignUp.showToast("Enter password");
            return false;
        }

        if (password.length() < 6) {
            mISignUp.showToast("Password has at least 6 characters");
            return false;
        }

        if (phone.isEmpty()) {
            mISignUp.showToast("Please enter your phone number");
            return false;
        }

        if (phone.length() != 9) {
            mISignUp.showToast("Invalid phone number");
            return false;
        }
        return true;
    }
}
