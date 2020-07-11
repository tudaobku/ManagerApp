package com.example.managerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.managerapp.ManagerSide.LoginPage;

public class MainPage extends AppCompatActivity {

    Button btnStall, btnCourt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStall = findViewById(R.id.btnStall);
        btnCourt = findViewById(R.id.btnCourt);

        btnStall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSupplierLoginPage();
            }
        });

        btnCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManagerLoginPage();
            }
        });
    }
    private void showSupplierLoginPage(){
        startActivity(new Intent(MainPage.this, com.example.managerapp.SupplierSide.Login.LoginPage.class));
    }
    private void showManagerLoginPage() {
        startActivity(new Intent(MainPage.this, LoginPage.class));
    }
}