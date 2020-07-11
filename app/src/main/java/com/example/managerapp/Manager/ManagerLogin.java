package com.example.managerapp.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managerapp.Common;
import com.example.managerapp.Manager.Interface.ManagerLoginContract;
import com.example.managerapp.Model.Manager;
import com.example.managerapp.R;
import com.example.managerapp.Supplier.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ManagerLogin extends AppCompatActivity implements ManagerLoginContract.View {

    EditText edtPhone, edtPassword;
    TextView txtRegister;
    Button btnLogin;
    DatabaseReference managerList;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;
    ManagerLoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        presenter = new ManagerLoginPresenter(this);
        managerList = FirebaseDatabase.getInstance().getReference("Manager");

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        String text = "Don't have an account? Register now";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(ManagerLogin.this, SignUpManager.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.rgb(30, 144, 255));
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 23,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtRegister.setText(ss);
        txtRegister.setMovementMethod(LinkMovementMethod.getInstance());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.login(edtPhone.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(ManagerLogin.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startHomePage() {
        startActivity(new Intent(this, ManagerHomePage.class));
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