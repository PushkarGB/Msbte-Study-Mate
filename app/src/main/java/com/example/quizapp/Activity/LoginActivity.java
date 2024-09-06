package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Ui View Objects
    Button loginBtn, signUpBtn;
    TextInputEditText email, pass;
    FirebaseAuth auth;

    Boolean doubleTap = false;
    android.app.ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        progressDialog = new android.app.ProgressDialog(this);
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
                                progressDialog.show();
                                try {
                                    /*Intent is used to move from one activity to another */

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    /* finish krne se user jab HomeActivity se back krega to wapis login pe nai ayega
                                rather he will exit the app */
                                } catch (Exception e) {
                                    /* to handle if there is a error with mainActivity.class activity not opening
                                    toast is to show popup message */
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}