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
        setContentView(R.layout.activity_manager_sign_up);

        edtName = findViewById(R.id.edit_text_name);
        edtPassword = findViewById(R.id.edit_text_password);
        btnRegister = findViewById(R.id.button_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                if (name.isEmpty()) {
                    showToast("Bạn chưa nhập tên tài khoản");
                }
                if (password.isEmpty()) {
                    showToast("Bạn chưa nhập mật khẩu");
                }
                if (password.length() < 6) {
                    showToast("Mật khẩu có ít nhất 6 ký tự");
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