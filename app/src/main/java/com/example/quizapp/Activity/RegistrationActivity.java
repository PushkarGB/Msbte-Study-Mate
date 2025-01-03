package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    //View Objects
    Button rg_signUpBtn;
    EditText rg_username, rg_email, rg_pass, rg_rePass;
    TextView rg_login;
    CircleImageView profileImage;

    //Database References
    FirebaseAuth auth; //authorization
    FirebaseDatabase database;  //real-time database
    FirebaseStorage storage;  //cloud storage

    //Image Uri and string
    Uri imageURI;
    String imageUriStr;

    //Loading msg
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);


        //setting up loading msg
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");
        progressDialog.setCancelable(false);

        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Please Wait...");
        progressDialog2.setCancelable(false);

        //initializing View Objects

        rg_signUpBtn = findViewById(R.id.registerBtn);
        rg_email = findViewById(R.id.regEmail);
        rg_pass = findViewById(R.id.regPassword);
        rg_username = findViewById(R.id.regUsername);
        rg_rePass = findViewById(R.id.reenterPass);
        rg_login = findViewById(R.id.redirectToSignIn);
        profileImage = findViewById(R.id.regProfile);

        //Redirect to Login Activity if user clicks on Sign In!

        rg_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Selecting Profile picture from gallery

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
            }
        });

        //Creating User i.e. Storing user data in database

        rg_signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting data from UI views
                String name = rg_username.getText().toString();
                String email = rg_email.getText().toString();
                String password = rg_pass.getText().toString();
                String rePass = rg_rePass.getText().toString();
                String status = "Hey! , I am using this application"; //default status

                /*creating objects(instances) of FirebaseAuth and all by using
                static method getInstance() which returns a instance of the Firebase database
                which will be singleton i.e. single instance for the entire project
                 * We can use getCurrentUser() to get the currently signed in user */
                auth = FirebaseAuth.getInstance();
                //get db instance
                database = FirebaseDatabase.getInstance();
                //get storage instance
                storage = FirebaseStorage.getInstance();

                //First check if all the fields are correctly entered

                if ((TextUtils.isEmpty(name)) || (TextUtils.isEmpty(email)) || (TextUtils.isEmpty(password)) || TextUtils.isEmpty(rePass)) {
                   progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Enter Valid Information", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                   progressDialog.dismiss();
                    rg_email.setError("Give proper email address!");
                } else if (password.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Password Need to have More than 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!rePass.equals(password)) {
                    progressDialog.dismiss();
                    rg_rePass.setError("Password Doesn't Match!");
                } else {

                    //If everything is correct , Then we will create a user
                    //and add a after its completion store data in database

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // if user was successfully created in authorization Then
                            if (task.isSuccessful()) {

                                //waiting screen
                                progressDialog2.show();

                                //First initialize  get id of user , create storage & db refs

                                String id = task.getResult().getUser().getUid(); //it may give nullPointer exception
                                DatabaseReference reference = database.getReference().child("user").child(id);
                                StorageReference storageReference = storage.getReference().child("Upload").child(id);

                                //If Profile image was set then put first put image in cloud storage & then

                                if (imageURI != null) {

                                    //after its completion
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                            //if image successfully uploaded

                                            if (task.isSuccessful()) {

                                                /*getting download url for this id(storageReference is referring to thia id in
                                                the storage)*/

                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageUriStr = uri.toString();

                                                        // driver class for getting ans setting data
                                                        Users users = new Users(id, name, imageUriStr, email, password, rePass, status);

                                                        //set values in the database
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                //if data successfully stored i.e user created successfully then
                                                                if (task.isSuccessful()) {

                                                                    //Loading screen
                                                                    progressDialog.show();

                                                                    //redirect to the Main activity
                                                                   // Toast.makeText(Registration.this, "reached here", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                //else show error
                                                                else {
                                                                    Toast.makeText(RegistrationActivity.this, "Error in Creating the User", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }

                                            //If image was not uploaded successfully
                                            else {
                                                Toast.makeText(RegistrationActivity.this, "Error in uploading Image!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                                //if Profile Image is not provided
                                //store a default image
                                else {
                                    //get access token of a default image uploaded on firebase
                                    imageUriStr = "https://firebasestorage.googleapis.com/v0/b/quizapp-c985a.appspot.com/o/user.png?alt=media&token=0d0cbfbe-bdff-489e-b0f9-844df0eb78c5";

                                    // driver class for getting ans setting data
                                    Users users = new Users(id, name, imageUriStr, email, password, rePass, status);

                                    //set values in the database
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //if data successfully stored i.e user created successfully then
                                            if (task.isSuccessful()) {

                                                //Loading screen
                                                progressDialog.show();

                                                //redirect to the Main activity

                                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            //else show error
                                            else {
                                                Toast.makeText(RegistrationActivity.this, "Error in Creating the User", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                            //Else if task was unsuccessful i.e. User was not successfully created in authorization
                            else {
                                Toast.makeText(RegistrationActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (data != null) {
                imageURI = data.getData();
                profileImage.setImageURI(imageURI);
            }
        }
    }
}
