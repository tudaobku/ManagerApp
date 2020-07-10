package com.example.managerapp.Supplier;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.managerapp.Supplier.Model.Account;
import com.example.managerapp.Model.Supplier;
import com.example.managerapp.Supplier.Interface.SupplierLoginContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SupplierLoginPresenter implements SupplierLoginContract.Presenter{
    Account account;
    DatabaseReference supplierList;
    SupplierLoginContract.View mILoginPage;
    public SupplierLoginPresenter(SupplierLoginContract.View iLoginCallBack) {
        mILoginPage = iLoginCallBack;
        supplierList = FirebaseDatabase.getInstance().getReference("Supplier/List");
    }

    @Override
    public void login(String phone, String password) {
        mILoginPage.showWaitingDialog();
        if(checkInput(phone, password)){
           supplierList.addListenerForSingleValueEvent(new ValueEventListener() {
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

    }

}
