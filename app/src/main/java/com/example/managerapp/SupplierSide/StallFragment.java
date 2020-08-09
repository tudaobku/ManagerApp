package com.example.managerapp.SupplierSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.managerapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class StallFragment extends Fragment {
    final private int RESULT_LOAD_IMAGE = 1;
    Uri tempUri;
    EditText edtName;
    Button btnUpload, btnUpdate;
    ImageView imgLogo;
    DatabaseReference supplierList;
    StorageReference storage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.stall_fragment, container, false);

        edtName = root.findViewById(R.id.edtName);
        imgLogo = root.findViewById(R.id.imgLogo);
        btnUpload = root.findViewById(R.id.btnUpload);
        btnUpdate = root.findViewById(R.id.btnUpdate);

        supplierList = FirebaseDatabase.getInstance().getReference("Supplier/List");
        storage = FirebaseStorage.getInstance().getReference();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadLogo();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });

        showAccount();
        return root;
    }
    private void uploadLogo(){
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn ảnh"), RESULT_LOAD_IMAGE);
    }

    private void updateAccount() {
        if(tempUri != null){
            final ProgressDialog mDialog = new ProgressDialog(getContext());
            mDialog.setMessage("Đang tải ảnh...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storage.child("logo" + imageName);
            imageFolder.putFile(tempUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Common.supplier.setImage(uri.toString());
                                    updateInformation();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int)(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Ảnh đã tải " + progress + "%");
                        }
                    });

        }
        else if(Common.supplier.getImage().isEmpty()){
            showUploadLogoDialog();
        }
        else updateInformation();
    }

    private void showAccount() {
        edtName.setText(Common.supplier.getName());
        if(!Common.supplier.getImage().isEmpty()) Picasso.with(getContext()).load(Common.supplier.getImage()).into(imgLogo);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            tempUri = data.getData();
            Picasso.with(getContext()).load(tempUri).into(imgLogo);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void updateInformation() {
        Common.supplier.setName(edtName.getText().toString());
        supplierList.child(Common.supplierPhone).setValue(Common.supplier);
        startActivity(new Intent(getContext(), HomePage.class));
    }

    private void showUploadLogoDialog() {
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setMessage("Bạn nên chọn logo cho stall của bạn")
                .setTitle("Cảnh báo!!!")
                .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        updateInformation();
                    }
                })
                .setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(R.drawable.warning)
                .create();
        alertDialog.show();
    }
}
