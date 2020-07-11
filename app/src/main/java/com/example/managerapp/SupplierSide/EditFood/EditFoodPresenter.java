package com.example.managerapp.SupplierSide.EditFood;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.managerapp.SupplierSide.Common;
import com.example.managerapp.SupplierSide.Menu.MenuInteractor;
import com.example.managerapp.SupplierSide.Model.Food;
import com.example.managerapp.SupplierSide.Model.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EditFoodPresenter implements EditFoodContract.Presenter {
    EditFoodContract.View editFoodView;
    EditFoodContract.Interactor editFoodInteractor;
    StorageReference storage;
    int position;
    Food food;
    Uri localUri;

    public EditFoodPresenter(EditFoodContract.View editFoodView) {
        this.editFoodView = editFoodView;
        editFoodInteractor = MenuInteractor.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        position = -1;
        food = new Food();
    }

    @Override
    public void loadFoodDetail(int position) {
        if(position > -1){
            this.position = position;
            food = editFoodInteractor.getFood(position);
            editFoodView.showFoodDetail(food);
        }
    }

    @Override
    public void saveFood(MenuItem menuItem) {
        if(checkInput(menuItem)){
            food.setMenuItem(menuItem);
            if(localUri != null){
                editFoodView.showProgress("Uploading Image...");
                String imageName = UUID.randomUUID().toString();
                final StorageReference imageFolder = storage.child("food-" + imageName);
                imageFolder.putFile(localUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        food.setImage(uri.toString());
                                        if(position == -1){
                                            food.setSupplierID(Common.supplier.getSupplierID());
                                            editFoodInteractor.performAddNewFood(food);
                                        }
                                        else editFoodInteractor.performUpdateFood(position, food);
                                        editFoodView.stopProgress();
                                        editFoodView.closeView();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                editFoodView.stopProgress();
                                editFoodView.showConnectionError();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                int progress = (int)(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                editFoodView.showProgress("Uploaded Image " + progress + "%");
                            }
                        });
            }
            else {
                if(position == -1) editFoodView.showInvalidMessage("Please add food image");
                else {
                    editFoodView.showProgress("Updating....");
                    editFoodInteractor.performUpdateFood(position, food);
                    editFoodView.stopProgress();
                    editFoodView.closeView();
                }

            }
        }
    }

    private boolean checkInput(MenuItem menuItem) {
        if(menuItem.getName().isEmpty()) {
            editFoodView.showInvalidMessage("Please fill food name");
            return false;
        }
        if(menuItem.getPrice().isEmpty()) {
            editFoodView.showInvalidMessage("Please fill food price");
            return false;
        }
        return true;
    }

    @Override
    public void saveLocalUri(Uri uri) {
        this.localUri = uri;
        editFoodView.showFoodImage(uri);
    }
}
