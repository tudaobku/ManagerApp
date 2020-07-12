package com.example.managerapp.ManagerSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managerapp.R;
import com.example.managerapp.Supplier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;


public class NewSupplier extends AppCompatActivity {

    DatabaseReference supplierList;
    FirebaseFunctions function;
    EditText edtName, edtPhone;
    Button btnAdd;
    TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_supplier);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnAdd = findViewById(R.id.btnAddSupplier);
        txtPassword = findViewById(R.id.txtPassword);
        supplierList = FirebaseDatabase.getInstance().getReference("Supplier/List");
        function = FirebaseFunctions.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addSupplier();
            }
        });


    }

    private void addSupplier() {


        final String name = edtName.getText().toString();
        final String phone = edtPhone.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(NewSupplier.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(NewSupplier.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(phone.length() != 10){
            Toast.makeText(NewSupplier.this,"Phone is invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog mDialog = new ProgressDialog(NewSupplier.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        supplierList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(phone).exists()) {
                    mDialog.dismiss();
                    Toast.makeText(NewSupplier.this, "Phone number exist!", Toast.LENGTH_SHORT).show();
                } else {
                   mDialog.dismiss();
                    Supplier newSupplier = new Supplier(name, txtPassword.getText().toString(), "","", "");
                    supplierList.child(phone).setValue(newSupplier).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(NewSupplier.this, "Add new supplier successfully!", Toast.LENGTH_SHORT).show();
                               finish();
                           }
                           else{
                               Toast.makeText(NewSupplier.this, "Something wrong", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}