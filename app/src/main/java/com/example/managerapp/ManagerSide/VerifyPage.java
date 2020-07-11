package com.example.managerapp.ManagerSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.managerapp.ManagerSide.Model.Manager;
import com.example.managerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyPage extends AppCompatActivity {

    Button btnVerify;
    EditText edtOTP;
    ProgressBar progressBar;
    String verificationCode;
    String name, password;
    String ownerPhone;
    DatabaseReference managerReference;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        btnVerify = (Button)findViewById(R.id.button_verify);
        edtOTP = (EditText)findViewById(R.id.edtOTP);
        progressBar = findViewById(R.id.progressbar1);

        progressBar.setVisibility(View.GONE);

        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        managerReference = FirebaseDatabase.getInstance().getReference("Manager");
        managerReference.child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ownerPhone = snapshot.getValue().toString();
                sendVerificationCodeToUser(ownerPhone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtOTP.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    edtOTP.setError("Wrong OTP...");
                    edtOTP.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, codeByUser);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Manager manager = new Manager(name, password);
                            managerReference.child("List").push().setValue(manager);
                            finish();
                            startActivity(new Intent(VerifyPage.this, LoginPage.class));
                        }else {
                            Toast.makeText(VerifyPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}