package com.example.managerapp.Manager;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.managerapp.Manager.Interface.ManagerLoginContract;
import com.example.managerapp.Manager.Model.ManagerAccount;
import com.example.managerapp.Model.Manager;
import com.example.managerapp.Model.Supplier;
import com.example.managerapp.Manager.Common;
import com.example.managerapp.Supplier.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerLoginPresenter implements ManagerLoginContract.Presenter {
    DatabaseReference managerList;
    ManagerAccount managerAccount;
    ManagerLoginContract.View mIManagerLogin;
    public ManagerLoginPresenter(ManagerLoginContract.View iLoginCallBack) {
        mIManagerLogin = iLoginCallBack;
        managerList = FirebaseDatabase.getInstance().getReference("Manager");
    }

    @Override
    public void login(String phone, String password) {
        mIManagerLogin.showWaitingDialog();
        if(checkInput(phone, password)) {
            managerList.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mIManagerLogin.closeWaitingDialog();
                    if (snapshot.child(managerAccount.getPhone()).exists()) {
                        Manager manager = snapshot.child(managerAccount.getPhone()).getValue(Manager.class);
                        if (manager.getPassword().equals(managerAccount.getPassword())) {
                            Common.manager = manager;
                            Common.managerPhone = managerAccount.getPhone();
                            mIManagerLogin.startHomePage();
                        } else {
                            mIManagerLogin.showToast("Wrong Password. Please try to enter password one more time");
                        }
                    }
                    else{
                        mIManagerLogin.showToast("Account is not exist");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            mIManagerLogin.closeWaitingDialog();
        }
    }

    private boolean checkInput(String phone, String password) {
        if(TextUtils.isEmpty(phone)){
            mIManagerLogin.showToast("Please Enter Phone");
            return false;
        }
        else if(phone.length() > 11 || phone.length() < 9){
            mIManagerLogin.showToast("Phone is invalid");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            mIManagerLogin.showToast("Please Enter Phone");
            return false;
        }
        managerAccount = new ManagerAccount(phone, password);
        return true;
    }

    @Override
    public void forgotPassword() {

    }
}
