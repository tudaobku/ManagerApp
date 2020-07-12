package com.example.managerapp.ManagerSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.managerapp.R;


public class HomePage extends AppCompatActivity {

    Button btnAddNewStall, btnManageStall, btnSignOut, btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);

        btnAddNewStall = (Button)findViewById(R.id.btnAddNewSupplier);
        btnManageStall = (Button)findViewById(R.id.btnManageStall);
        btnSignOut = (Button)findViewById(R.id.btnSignOut);
        btnReport = findViewById(R.id.btnReport);
        btnAddNewStall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, NewSupplier.class));
            }
        });

        btnManageStall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, SupplierList.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Sign out", Toast.LENGTH_SHORT).show();
                Intent signIn = new Intent(HomePage.this, LoginPage.class);
                signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signIn);
                finish();
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, ReportPage.class));
            }
        });
    }
}