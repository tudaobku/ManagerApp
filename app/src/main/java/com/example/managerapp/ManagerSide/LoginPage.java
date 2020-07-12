package com.example.managerapp.ManagerSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managerapp.ManagerSide.Model.Manager;
import com.example.managerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginPage extends AppCompatActivity {

    EditText edtName, edtPassword;
    TextView txtRegister;
    Button btnLogin;
    DatabaseReference managerReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        managerReference = FirebaseDatabase.getInstance().getReference("Manager/List");

        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, SignUpPage.class));

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        final String name = edtName.getText().toString();
        final String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(LoginPage.this, "Please Enter Account Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginPage.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
            return;
        }
        managerReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot foodSnapshot: snapshot.getChildren()){
                    Manager manager = foodSnapshot.getValue(Manager.class);
                    if(manager.getName().equals(name)){
                        if (manager.getPassword().equals(password)) {
                            startActivity(new Intent(LoginPage.this, HomePage.class));
                        } else {
                            Toast.makeText(LoginPage.this, "Wrong Password. Please try to enter password one more time", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
