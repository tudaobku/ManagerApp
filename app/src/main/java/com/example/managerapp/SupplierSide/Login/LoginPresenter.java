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
                            mILoginPage.showToast("Mật khẩu không đúng");
                       }
                   }
                   else{
                       mILoginPage.showToast("Tài khoản không tồn tại");
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   mILoginPage.closeWaitingDialog();
                   mILoginPage.showToast("Lỗi hệ thống");
               }
           });
        }
        else{
            mILoginPage.closeWaitingDialog();
        }
    }
    private boolean checkInput(String phone, String password){
        if(TextUtils.isEmpty(phone)){
            mILoginPage.showToast("Hãy nhập số điện thoại");
            return false;
        }
        else if(phone.length() != 10){
            mILoginPage.showToast("Số điện thoại không hợp lệ");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            mILoginPage.showToast("Hãy nhập mật khẩu");
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
    public void resetPassword(final String phone, final String password) {
        mILoginPage.showWaitingDialog();
        supplierReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mILoginPage.closeWaitingDialog();
                if (snapshot.child(phone).exists()) {
                    supplierReference.child(phone).child("password").setValue(password);
                    mILoginPage.showToast("Thay đổi mật khẩu thành công");

                }
                else{
                    mILoginPage.showAccountErrorDialog("Tài khoản không tồn tại", "Bạn có phải là một nhà cung cấp?");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mILoginPage.closeWaitingDialog();
                mILoginPage.showToast("Lỗi hệ thống");
            }
        });
    }


}
