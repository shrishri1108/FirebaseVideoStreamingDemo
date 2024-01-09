package com.example.firebasevideostreaming;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasevideostreaming.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    private FirebaseAuth firebaseAuth;
    private String login_email,login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();

        loginBinding.btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginBinding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_email= loginBinding.etEmailLogin.getText().toString();
                login_password= loginBinding.etPasswordLogin.getText().toString();
                if(login_email.isEmpty()){
                    loginBinding.etEmailLogin.setError("Mandatory Field ");
                    loginBinding.etEmailLogin.requestFocus();
                }
                else if(login_password.isEmpty()){
                    loginBinding.etPasswordLogin.setError(" Mandatory Field ");
                    loginBinding.btnNewUser.requestFocus();
                }
                else {
                    loginToFirebase();
                    Intent intent=new Intent(LoginActivity.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loginToFirebase() {
        firebaseAuth.signInWithEmailAndPassword(login_email,login_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sign In Success . 
                            Log.d(TAG, "onComplete:  success,  login email and password sign in . " );
                            Toast.makeText(LoginActivity.this, " Congrats! Sign In is successful !  ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user= firebaseAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            // Email and password sign in failed !.  user not registered . 
                            Log.w(TAG, "onComplete:  signInFailure " , task.getException());
                            Toast.makeText(LoginActivity.this, " Sign In Failed "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent intent=new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}