package com.example.managerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.managerapp.Model.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class FoodDetail extends AppCompatActivity {

    final private int RESULT_LOAD_IMAGE = 1;
    EditText edtName, edtPrice, edtDiscount, edtDes;
    ImageView imageFood;
    Button btnUpdate;
    Uri tempUri;
    String foodID;
    DatabaseReference foodList;
    StorageReference storage;
    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        
        imageFood = findViewById(R.id.imageFood);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDiscount = findViewById(R.id.edtDiscount);
        edtDes = findViewById(R.id.edtDescription);
        btnUpdate = findViewById(R.id.btnUpdate);

        foodList = FirebaseDatabase.getInstance().getReference("Food");
        storage = FirebaseStorage.getInstance().getReference();

        imageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFood();
            }
        });

        if (getIntent(  ) != null)
        {
            foodID = getIntent().getStringExtra("foodID");
            if (!foodID.isEmpty())
            {
                loadFoodDetail(foodID);
            }
        }

    }

    private void uploadImage() {
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);
    }

    private void updateFood() {
        food.setDescription(edtDes.getText().toString());
        food.setName(edtName.getText().toString());
        food.setPrice(edtPrice.getText().toString());
        food.setDiscount(edtDiscount.getText().toString());

        if(tempUri != null){
            final ProgressDialog mDialog = new ProgressDialog(FoodDetail.this);
            mDialog.setMessage("Uploading Image...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storage.child("food" + imageName);
            imageFolder.putFile(tempUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    food.setImage(uri.toString());
                                    foodList.child(foodID).setValue(food);
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(FoodDetail.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int)(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded Image" + progress + "%");
                        }
                    });
        }
        else {
            foodList.child(foodID).setValue(food);
            finish();
        }
    }

    private void loadFoodDetail(String foodID) {
        foodList.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    food = dataSnapshot.getValue(Food.class);
                    Picasso.with(getBaseContext()).load(food.getImage()).into(imageFood);
                    edtPrice.setText(food.getPrice());
                    edtName.setText(food.getName());
                    edtDes.setText(food.getDescription());
                    edtDiscount.setText(food.getDiscount());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            tempUri = data.getData();
            Picasso.with(FoodDetail.this).load(tempUri).into(imageFood);
        }
    }
}