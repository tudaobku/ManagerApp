package com.example.managerapp.SupplierSide.Login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.managerapp.ManagerSide.NewSupplier;
import com.example.managerapp.R;
import com.example.managerapp.VerifyPage;

public class ResetPasswordDialog extends AppCompatDialogFragment {
    EditText edtPhone, edtPass, edtConfirmPass;
    Button btnSubmit;
    Listener listener;
    String pass, phone;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reset_password_layout, null);
        builder.setView(view);
        builder.setTitle("Tạo mật khẩu mới");
        builder.setCancelable(true);
        edtPhone = view.findViewById(R.id.edtName);
        edtPass = view.findViewById(R.id.edtPass);
        edtConfirmPass = view.findViewById(R.id.edtConfirmPass);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = edtPass.getText().toString();
                phone = edtPhone.getText().toString();

                if(phone.isEmpty()){
                    Toast.makeText(getContext(),"Hãy nhập số điện thoại",Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty()){
                    Toast.makeText(getContext(),"Hãy nhập mật khẩu mới",Toast.LENGTH_SHORT).show();
                }
                else if(phone.length() != 10){
                    Toast.makeText(getContext(),"Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length() < 6){
                    Toast.makeText(getContext(),"Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(edtConfirmPass.getText().toString())){
                    Toast.makeText(getContext(),"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getContext(), VerifyPage.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("type", "supplier");
                    startActivityForResult(intent, 1);

                }


            }
        });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                listener.resetPassword(phone, pass);
                dismiss();
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    public interface Listener {
        void resetPassword(String phone, String password);
    }
}
