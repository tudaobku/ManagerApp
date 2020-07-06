package com.example.managerapp.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managerapp.Common;
import com.example.managerapp.Model.Manager;
import com.example.managerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ManagerLogin extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    TextView txtRegister;
    Button btnLogin;
    DatabaseReference managerList;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);


        managerList = FirebaseDatabase.getInstance().getReference("Manager");

        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        //txtRegister.setMovementMethod(LinkMovementMethod.getInstance());
        btnLogin = (Button)findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        String text = "Don't have an account? Register now";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(ManagerLogin.this, SignUpManager.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.rgb(30, 144, 255));
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 23,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtRegister.setText(ss);
        txtRegister.setMovementMethod(LinkMovementMethod.getInstance());

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
                if (snapshot.child(edtEmail.getText().toString()).exists()) {
                    mDialog.dismiss();
                    Manager manager = snapshot.child(edtEmail.getText().toString()).getValue(Manager.class);
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
                final ProgressDialog mDialog = new ProgressDialog(ManagerLogin.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mDialog.dismiss();
                                if (task.isSuccessful()) {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        startActivity(new Intent(ManagerLogin.this, ManagerHomePage.class));
                                    }else {
                                        Toast.makeText(ManagerLogin.this, "Please verify your email address!", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(ManagerLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}