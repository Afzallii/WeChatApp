package com.example.socialmediaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 101;
    private EditText etUsername, etProfession, etCity, etCountry;
    private Button bDone;
    CircleImageView profile_image;
    Uri imageUri;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    ProgressDialog mLoadingBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        toolbar=findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Setup Profile");
        mLoadingBar=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile Image");
        etUsername = findViewById(R.id.etUserName);
        etProfession = findViewById(R.id.etProfession);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCounty);
        bDone = findViewById(R.id.bDone);
        profile_image = findViewById(R.id.profile_img);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();
            }
        });
    }

    private void saveDate() {
        String username = etUsername.getText().toString();
        String profession = etProfession.getText().toString();
        String city = etCity.getText().toString();
        String country = etCountry.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            showError(etUsername, "User name is not valid");
        } else if (profession.isEmpty() || profession.length() < 3) {
            showError(etProfession, "Invalid Profession type");
        } else if (city.isEmpty() || city.length() < 3) {
            showError(etCity, "Invalid City Type");
        } else if (country.isEmpty() || country.length() < 3) {
            showError(etCountry, "Invalid Country Type");
        } else if (imageUri == null) {
            Toast.makeText(SetupActivity.this, "Please select a photo", Toast.LENGTH_SHORT).show();
        } else {

            mLoadingBar.setTitle("Setup is being Processed");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            storageReference.child(firebaseUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    storageReference.child(firebaseUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap hashMap = new HashMap();
                            hashMap.put("City", city);
                            hashMap.put("Username", username);
                            hashMap.put("Profession", profession);
                            hashMap.put("Country", country);
                            hashMap.put("profile image",uri.toString());
                            hashMap.put("status","offline");


                            databaseReference.child(firebaseUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    mLoadingBar.dismiss();
                                    Intent intent=new Intent(SetupActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(SetupActivity.this, "Setup Completed", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mLoadingBar.dismiss();
                            Toast.makeText(SetupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                }
            });
        }
    }

    private void showError(EditText field, String text) {
        field.setTextColor(getResources().getColor(R.color.Red));
        field.setText(text);
        field.requestFocus();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            imageUri=data.getData();
            profile_image.setImageURI(imageUri);
        }
    }
}