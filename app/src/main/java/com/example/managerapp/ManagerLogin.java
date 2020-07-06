package com.example.managerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.managerapp.Model.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerLogin extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnLogin;
    DatabaseReference managerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        managerList = FirebaseDatabase.getInstance().getReference("Manager/List");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkAccount();
            }
        });
    }

    private void checkAccount() {
        final ProgressDialog mDialog = new ProgressDialog(ManagerLogin.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();
        managerList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(edtPhone.getText().toString()).exists()) {
                    mDialog.dismiss();
                    Manager manager = snapshot.child(edtPhone.getText().toString()).getValue(Manager.class);
                    if (manager.getPassword() != null && edtPassword.getText() != null && manager.getPassword().equals(edtPassword.getText().toString())) {
                        Common.manager = manager;
                        startActivity(new Intent(ManagerLogin.this, ManagerHomePage.class));
                        finish();
                    } else {
                        Toast.makeText(ManagerLogin.this, "Failed to sign in!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(ManagerLogin.this, "Manager is not exist!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}