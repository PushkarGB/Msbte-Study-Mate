package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminNewsUploadActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private DatePicker dateEditText;
    private ImageView thumbnailImageView;
    private Button uploadButton, selectDocumentButton;

    private Uri thumbnailUri, documentUri;

    private DatabaseReference newsDatabaseRef;
    private StorageReference storageReference;

    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Intent> selectDocumentLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news_upload);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        titleEditText = findViewById(R.id.titleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        contentEditText = findViewById(R.id.contentEditText);
        thumbnailImageView = findViewById(R.id.thumbnailImageView);
        uploadButton = findViewById(R.id.uploadButton);
        selectDocumentButton = findViewById(R.id.selectDocumentButton);

        newsDatabaseRef = FirebaseDatabase.getInstance().getReference("News");
        storageReference = FirebaseStorage.getInstance().getReference("NewsUploads");

        progressDialog = new ProgressDialog(this);

        // Initialize ActivityResultLaunchers
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        thumbnailUri = result.getData().getData();
                        if (thumbnailUri != null) {
                            ContentResolver contentResolver = getContentResolver();
                            contentResolver.takePersistableUriPermission(thumbnailUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            thumbnailImageView.setImageURI(thumbnailUri);
                        }
                    }
                }
        );

        selectDocumentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        documentUri = result.getData().getData();
                        if (documentUri != null) {
                            ContentResolver contentResolver = getContentResolver();
                            contentResolver.takePersistableUriPermission(documentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Toast.makeText(this, "Document Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        selectDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDocument();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadNewsArticle();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        selectImageLauncher.launch(intent);
    }

    private void selectDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        selectDocumentLauncher.launch(intent);
    }

    private void uploadNewsArticle() {
        String title = titleEditText.getText().toString();
        String date = dateEditText.getYear() + "/" + (dateEditText.getMonth() + 1) + "/" + dateEditText.getDayOfMonth();
        String content = contentEditText.getText().toString();

        if (title.isEmpty() || content.isEmpty() || thumbnailUri == null || documentUri == null) {
            Toast.makeText(this, "Please fill all fields and select a thumbnail and a document.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        StorageReference thumbnailRef = storageReference.child("Thumbnails/" + timestamp + ".jpg");
        StorageReference documentRef = storageReference.child("Documents/" + timestamp + ".pdf");

        thumbnailRef.putFile(thumbnailUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    thumbnailRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String thumbnailUrl = task.getResult().toString();

                                documentRef.putFile(documentUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            documentRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    if (task.isSuccessful()) {
                                                        String documentUrl = task.getResult().toString();

                                                        HashMap<String, String> newsData = new HashMap<>();
                                                        newsData.put("title", title);
                                                        newsData.put("date", date);
                                                        newsData.put("content", content);
                                                        newsData.put("thumbnailUrl", thumbnailUrl);
                                                        newsData.put("documentUrl", documentUrl);

                                                        newsDatabaseRef.child(timestamp).setValue(newsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressDialog.dismiss();
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(AdminNewsUploadActivity.this, "News Article Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                } else {
                                                                    Log.e("FirebaseError", task.getException().getMessage() + " ");
                                                                    Toast.makeText(AdminNewsUploadActivity.this, "Failed to upload news article.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(AdminNewsUploadActivity.this, "Failed to upload document.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AdminNewsUploadActivity.this, "Failed to upload thumbnail.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}