package com.example.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout rConfirm,rEmail,rPassword;
    private Button bRegister;
    private TextView tvAlreadyHaveAccount;
     FirebaseAuth mAuth;
     ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        rConfirm=findViewById(R.id.rConfirm);
        rEmail=findViewById(R.id.rEmail);
        rPassword=findViewById(R.id.rPassword);
        bRegister=findViewById(R.id.bRegister);
        tvAlreadyHaveAccount=findViewById(R.id.tvAlreadyHaveAccount);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptRegistration();
            }
        });

        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AttemptRegistration() {
        String Email=rEmail.getEditText().getText().toString();
        String Password=rPassword.getEditText().getText().toString();
        String Confirm=rConfirm.getEditText().getText().toString();

        if(Email.isEmpty()||!Email.contains("@gmail")){
            showError(rEmail,"Email is not valid");
        }
        else if(Password.isEmpty()||Password.length()<5){
            showError(rPassword,"Password must be greater than 5 words");
        }
        else if(!Confirm.equals(Password)){
            showError(rConfirm, "Password did not matched");
        }else{
            mDialog.setTitle("Registration");
            mDialog.setMessage("Please for a while");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,SetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {mDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Registration is failed",Toast.LENGTH_SHORT).show();}
                }
            });
        }
    }

    private void showError(TextInputLayout field , String text) {
        field.setError(text);
        field.requestFocus();
    }
}