package com.example.firebasevideostreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasevideostreaming.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private String register_email,register_password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        goToDashboardIfAlreadyLoggedIn();
        mainBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_email= mainBinding.etUserId.getText().toString();
                register_password= mainBinding.etPassword.getText().toString();
                if(register_email.isEmpty()){
                    mainBinding.etUserId.setError("Mandatory Field ");
                    mainBinding.etUserId.requestFocus();
                }
                else if(register_password.isEmpty()){
                    mainBinding.etPassword.setError(" Mandatory Field ");
                    mainBinding.etPassword.requestFocus();
                }
                else {
                    registerUserIntoFirebase();
                }
            }
        });

        mainBinding.btnAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUserIntoFirebase() {
        mainBinding.progressBar.setVisibility(View.VISIBLE);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(register_email,register_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mainBinding.progressBar.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Intent intent= new Intent(MainActivity.this, Dashboard.class);
                            Toast.makeText(MainActivity.this,  "Successfully Registered! ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user =firebaseAuth.getCurrentUser();
                            startActivity(intent);
                            finish();
                        }
                        else{
                            mainBinding.etUserId.setText("");
                            mainBinding.etPassword.setText("");
                            Toast.makeText(MainActivity.this, " Error in Registering is : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goToDashboardIfAlreadyLoggedIn() {
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent =new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }

    }
}