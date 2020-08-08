package com.example.managerapp.SupplierSide.Login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.managerapp.R;

public class ResetPasswordDialog extends AppCompatDialogFragment {
    EditText edtPhone, edtPass, edtConfirmPass;
    Button btnSubmit;
    Listener listener;
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
                String pass = edtPass.getText().toString();
                String phone = edtPhone.getText().toString();
                if(!pass.equals(edtConfirmPass.getText().toString())){
                    Toast.makeText(getContext(),"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
                }
                else if(phone.isEmpty()){
                    Toast.makeText(getContext(),"Hãy nhập số điện thoại",Toast.LENGTH_SHORT).show();
                }
                else{
                    listener.resetPassword(phone, pass);
                    dismiss();
                }


            }
        });
        return builder.create();
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
