package com.example.managerapp.Supplier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managerapp.R;

import com.example.managerapp.Supplier.Interface.SupplierLoginContract;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SupplierLoginPage extends AppCompatActivity implements SupplierLoginContract.View {

    Button btnLogin;
    MaterialEditText edtPhone, edtPassword;
    TextView txtForgotPass;
    SupplierLoginPresenter presenter;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_login);
        presenter = new SupplierLoginPresenter(this);
        btnLogin = findViewById(R.id.btnLogin);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.login(edtPhone.getText().toString(), edtPassword.getText().toString());
            }
        });

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(SupplierLoginPage.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startHomePage() {
        startActivity(new Intent(this, SupplierHomePage.class));
        finish();
    }

    @Override
    public void showWaitingDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();
    }

    @Override
    public void closeWaitingDialog() {
        mDialog.dismiss();

    }
}