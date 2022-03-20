package com.example.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout ipEmail,ipPassword;
    private Button bEnter;
    private TextView tvForgetPassword,tvCreateAccount;
    ProgressDialog mDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        ipEmail=findViewById(R.id.ipEmail);
        ipPassword=findViewById(R.id.ipPassword);
        bEnter=findViewById(R.id.bEnter);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        tvCreateAccount=findViewById(R.id.tvCreateAccount);

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        bEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin();
            }
        });
    }

    private void AttemptLogin() {
        String Email=ipEmail.getEditText().getText().toString();
        String Password=ipPassword.getEditText().getText().toString();

        if(Email.isEmpty()||!Email.contains("@gmail")){
            showError(ipEmail,"Email is not valid");
        }
        else if(Password.isEmpty()||Password.length()<5){
            showError(ipPassword,"Password must be greater than 5 words");
        }else{
            mDialog.setTitle("Login");
            mDialog.setMessage("Please for a while");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        mDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login is failed",Toast.LENGTH_SHORT).show();}
                }
            });

}
}
    private void showError(TextInputLayout field , String text) {
        field.setError(text);
        field.requestFocus();
    }

}

