package com.example.managerapp.Supplier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.managerapp.Supplier.Interface.EditFoodContract;
import com.example.managerapp.Supplier.Model.Food;
import com.example.managerapp.R;
import com.example.managerapp.Supplier.Model.MenuItem;
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

public class EditFoodActivity extends AppCompatActivity implements EditFoodContract.View {
    EditFoodContract.Presenter presenter;
    EditText edtName, edtPrice, edtDiscount, edtDes;
    ImageView imageFood;
    Button btnUpdate;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        
        imageFood = findViewById(R.id.imageFood);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDiscount = findViewById(R.id.edtDiscount);
        edtDes = findViewById(R.id.edtDescription);
        btnUpdate = findViewById(R.id.btnUpdate);

        presenter = new EditFoodPresenter(this);
        int position = getIntent().getIntExtra(Common.EXTRA_FOOD_POSITION, -1);
        if (position != -1) {
            presenter.loadFoodDetail(position);
        }
        else btnUpdate.setText(R.string.addfood);
        progressDialog = new ProgressDialog(this);
        imageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuItem menuItem = new MenuItem();
                menuItem.setDescription(edtDes.getText().toString());
                menuItem.setName(edtName.getText().toString());
                menuItem.setPrice(edtPrice.getText().toString());
                menuItem.setDiscount(edtDiscount.getText().toString());
                presenter.saveFood(menuItem);
            }
        });
    }

    private void chooseImage() {
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), Common.RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Common.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            presenter.saveLocalUri(data.getData());
        }
    }

    @Override
    public void showInvalidMessage(String message) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
        alertDialog.setTitle("Invalid Food")
                .setMessage(message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(R.drawable.warning)
                .create();
        alertDialog.show();
    }

    @Override
    public void showFoodDetail(Food food) {
        Picasso.with(getBaseContext()).load(food.getImage()).into(imageFood);
        edtPrice.setText(food.getPrice());
        edtName.setText(food.getName());
        edtDes.setText(food.getDescription());
        edtDiscount.setText(food.getDiscount());
    }

    @Override
    public void showFoodImage(Uri uri) {
        Picasso.with(this).load(uri).into(imageFood);
    }

    @Override
    public void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void stopProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showConnectionError() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
        alertDialog.setTitle("Connection Error")
                .setMessage("Please check your connection")
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(R.drawable.warning)
                .create();
        alertDialog.show();
    }

    @Override
    public void closeView() {
        finish();
    }
}