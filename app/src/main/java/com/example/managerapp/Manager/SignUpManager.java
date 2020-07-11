package com.example.managerapp.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.managerapp.Manager.Interface.SignUpManagerContract;
import com.example.managerapp.Model.Manager;
import com.example.managerapp.R;
import com.example.managerapp.Supplier.HomePage;
import com.example.managerapp.Supplier.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpManager extends AppCompatActivity implements SignUpManagerContract.View {

    private EditText edtName, edtEmail, edtPassword, edtPhone;
    private ProgressBar progressBar;
    private Button btnRegister;
    SignUpManagerPresenter presenter;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_manager);

        presenter = new SignUpManagerPresenter(this);
        edtName = findViewById(R.id.edit_text_name);
        edtEmail = findViewById(R.id.edit_text_email);
        edtPassword = findViewById(R.id.edit_text_password);
        edtPhone = findViewById(R.id.edit_text_phone);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        btnRegister = findViewById(R.id.button_register);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.registerUser(
                        edtName.getText().toString(),
                        edtEmail.getText().toString(),
                        edtPhone.getText().toString(),
                        edtPassword.getText().toString()
                );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }



    @Override
    public void showToast(String message) {
        Toast.makeText(SignUpManager.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startHomePage(String name, String email, String password, String phone) {
        closeWaitingDialog();
        Intent intent = new Intent(this, VerifyPhoneNo.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }

    @Override
    public void showWaitingDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeWaitingDialog() {
        progressBar.setVisibility(View.GONE);
    }
}