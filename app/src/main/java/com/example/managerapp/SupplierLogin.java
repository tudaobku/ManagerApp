package com.example.managerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.managerapp.Model.Supplier;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SupplierLogin extends AppCompatActivity {

    Button btnLogin;
    MaterialEditText edtPhone, edtPassword;
    DatabaseReference supplierList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_login);

        btnLogin = findViewById(R.id.btnLogin);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        supplierList = FirebaseDatabase.getInstance().getReference("Supplier/List");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              checkAccount();
            }
        });

    }


    private void checkAccount() {
        final ProgressDialog mDialog = new ProgressDialog(SupplierLogin.this);
        mDialog.setMessage("Please waiting");
        mDialog.show();

        supplierList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(edtPhone.getText().toString()).exists()) {
                    mDialog.dismiss();
                    Supplier supplier = snapshot.child(edtPhone.getText().toString()).getValue(Supplier.class);
                    if (supplier.getPassword().equals(edtPassword.getText().toString())) {
                        Common.supplier = supplier;
                        Common.supplierPhone = edtPhone.getText().toString();
                        finish();
                        startActivity(new Intent(SupplierLogin.this, SupplierHomePage.class));
                    } else {
                        Toast.makeText(SupplierLogin.this, "Wrong password", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    mDialog.dismiss();
                    Toast.makeText(SupplierLogin.this, "Account is not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}