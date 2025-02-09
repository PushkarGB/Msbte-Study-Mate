package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class LoginActivity extends AppCompatActivity {

    // Ui View Objects
    Button loginBtn, signUpBtn;
    TextInputEditText email, pass;
    FirebaseAuth auth;

    Boolean doubleTap = false;
    ProgressDialog progressDialog;

    private static final String TAG = "LoginActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bhai aa raha hoon,\n nikal gaya bas");
        progressDialog.setCancelable(false); //if its true user will see a cancel buttion

        auth = FirebaseAuth.getInstance();

        //initialize view objects
        loginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.logEmail);
        pass = findViewById(R.id.logPassword);
        signUpBtn = findViewById(R.id.redirectToSignUp);

        //Redirect to Registration

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        /*On Back Press -- Kyuki hamne observe kiya tha ki HOme screen se logout krne ke bad jab Hum waps login pe ate he
        to wha se fir back kiye to exit hone ki jagah home screen pe ja rahe the */

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (doubleTap) {
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
                    doubleTap = true;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleTap = false;
                        }
                    }, 2000);
                }
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        //Login Process

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting data from UI views

                String Email = email.getText().toString();
                String Pass = pass.getText().toString();

                //check if fields are correct - validation

                if ((TextUtils.isEmpty(Email))) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                } else if ((TextUtils.isEmpty(Pass))) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    progressDialog.dismiss();
                    email.setError("Give Proper Email Address!");
                } else if (Pass.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Password Need to have More than 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    //If all fields are correct sign in
                    auth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        /* onComplete means jab database se kam ho jaye uske bad check krenge ki task
                        successful he to kya krna and vice versa */

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            /* Nonnull means if it is null our method
                            will not execute and throw error/exception */

                            //If Login was successful then
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    user.getIdToken(true)
                                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                    if (task.isSuccessful()) {
                                                        GetTokenResult result = task.getResult();
                                                        if (result != null && result.getClaims() != null) {
                                                            Boolean isAdmin = (Boolean) result.getClaims().get("admin");
                                                            if (isAdmin != null && isAdmin) {
                                                                // User is an admin, go to AdminActivity
                                                                Log.d(TAG, "User is an admin");
                                                                Intent intent = new Intent(LoginActivity.this, AdminNewsUploadActivity.class);
                                                                startActivity(intent);
                                                            } else {
                                                                // User is not an admin, go to HomeActivity
                                                                Log.d(TAG, "User is not an admin");
                                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                            finish();
                                                        } else {
                                                            progressDialog.dismiss();
                                                            Log.e(TAG, "Claims are null");
                                                            Toast.makeText(LoginActivity.this, "Failed to get user claims.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        progressDialog.dismiss();
                                                        Log.e(TAG, "Failed to get token", task.getException());
                                                        Toast.makeText(LoginActivity.this, "Failed to get user token.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}