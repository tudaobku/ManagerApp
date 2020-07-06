package com.example.managerapp.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.managerapp.R;


public class ManagerHomePage extends AppCompatActivity {

    Button btnAddNewStall, btnDeleteStall, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);

        btnAddNewStall = (Button)findViewById(R.id.btnAddNewStall);
        btnDeleteStall = (Button)findViewById(R.id.btnDeleteStall);
        btnSignOut = (Button)findViewById(R.id.btnSignOut);

        btnAddNewStall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerHomePage.this, NewSupplier.class));
            }
        });

        btnDeleteStall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerHomePage.this, SupplierList.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Sign out", Toast.LENGTH_SHORT).show();
                Intent signIn = new Intent(ManagerHomePage.this, ManagerLogin.class);
                signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signIn);
                finish();
            }
        });
    }
}