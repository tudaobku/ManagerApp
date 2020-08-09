package com.example.managerapp.ManagerSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.managerapp.R;
import com.example.managerapp.VerifyPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginPage extends AppCompatActivity {

    EditText edtPhone;
    Button btnLogin;
    DatabaseReference managerReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        managerReference = FirebaseDatabase.getInstance().getReference("Manager/phone");

        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(LoginPage.this, "Hãy nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
                else{
                    managerReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String phone = snapshot.getValue().toString();
                            if(phone.equals(edtPhone.getText().toString())){
                                Intent intent = new Intent(LoginPage.this, VerifyPage.class);
                                intent.putExtra("phone", phone);
                                intent.putExtra("type", "manager");
                                startActivity(intent);
                            }
                           else{
                               Toast.makeText(LoginPage.this,"Số điện thoại không đúng",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}
