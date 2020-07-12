package com.example.managerapp.SupplierSide.Login;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.managerapp.SupplierSide.Common;
import com.example.managerapp.SupplierSide.Model.Account;
import com.example.managerapp.Supplier;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter implements LoginContract.Presenter{
    Account account;
    DatabaseReference supplierReference;
    LoginContract.View mILoginPage;
    public LoginPresenter(LoginContract.View iLoginCallBack) {
        mILoginPage = iLoginCallBack;
        supplierReference = FirebaseDatabase.getInstance().getReference("Supplier/List");
    }

    @Override
    public void login(String phone, String password) {
        mILoginPage.showWaitingDialog();
        if(checkInput(phone, password)){
           supplierReference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   mILoginPage.closeWaitingDialog();
                   if (snapshot.child(account.getPhone()).exists()) {
                       Supplier supplier = snapshot.child(account.getPhone()).getValue(Supplier.class);
                       if (supplier.getPassword().equals(account.getPassword())) {
                           Common.supplier = supplier;
                           Common.supplierPhone = account.getPhone();
                           mILoginPage.startHomePage();
                       } else {
                            mILoginPage.showToast("Wrong Password. Please try to enter password one more time");
                       }
                   }
                   else{
                       mILoginPage.showToast("Account is not exist");
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   mILoginPage.closeWaitingDialog();
                   mILoginPage.showToast("Something wrong");
               }
           });
        }
        else{
            mILoginPage.closeWaitingDialog();
        }
    }
    private boolean checkInput(String phone, String password){
        if(TextUtils.isEmpty(phone)){
            mILoginPage.showToast("Please Enter Phone");
            return false;
        }
        else if(phone.length() > 11 || phone.length() < 9){
            mILoginPage.showToast("Phone is invalid");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            mILoginPage.showToast("Please Enter Phone");
            return false;
        }
        account = new Account(phone, password);
        return true;
    }
    @Override
    public void forgotPassword() {
        mILoginPage.showResetPasswordDialog();
    }

    @Override
    public void resetPassword(final String phone, final String email, final String password) {
        mILoginPage.showWaitingDialog();
        supplierReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mILoginPage.closeWaitingDialog();
                if (snapshot.child(phone).exists()) {
                    Supplier supplier = snapshot.child(phone).getValue(Supplier.class);
                    if (supplier.getEmail().equals(email)) {
                        supplierReference.child(phone).child("password").setValue(password);
                        mILoginPage.showToast("Reset Password Successfully");
                    } else {
                        mILoginPage.showAccountErrorDialog("Wrong Email", "Please try again");
                    }
                }
                else{
                    mILoginPage.showAccountErrorDialog("Account doest not exist", "You are the court's supplier. Sure?");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mILoginPage.closeWaitingDialog();
                mILoginPage.showToast("Something wrong");
            }
        });
    }


}
