package com.example.managerapp.ManagerSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.managerapp.R;

public class SignUpPage extends AppCompatActivity {

    private EditText edtName, edtPassword;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_manager);

        edtName = findViewById(R.id.edit_text_name);
        edtPassword = findViewById(R.id.edit_text_password);
        btnRegister = findViewById(R.id.button_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                if (password.isEmpty()) {
                    if (name.isEmpty()) {
                        showToast("Please enter your name");
                    }
                    showToast("Enter password");
                   }

                else if (password.length() < 6) {
                    showToast("Password has at least 6 characters");
                }
                else{
                    Intent intent = new Intent(SignUpPage.this, VerifyPage.class);
                    intent.putExtra("name", name);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(SignUpPage.this, message, Toast.LENGTH_SHORT).show();
    }

}