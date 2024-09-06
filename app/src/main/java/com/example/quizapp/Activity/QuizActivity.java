package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.quizapp.Domain.Question;
import com.example.quizapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuizActivity extends AppCompatActivity {
    private ImageView titleIcon;
    private TextView title, weight;
    private boolean doubleTap  = false;
    String course,unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        course = getIntent().getStringExtra("course");
        unit = getIntent().getStringExtra("unitId");
        String weightStr = getIntent().getStringExtra("weight");
        String unitName = getIntent().getStringExtra("unitName");
        int resourceId = getIntent().getIntExtra("courseImgResource", R.drawable.ajp);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new QuizFragment(course, unit)).commit();

        titleIcon = findViewById(R.id.quizTitleimg);
        title = findViewById(R.id.quizTitle);
        weight = findViewById(R.id.quizWeight);

        Glide.with(this).load(resourceId).into(titleIcon);
        title.setText(unitName);
        weight.setText("Weightage : " + weightStr);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (doubleTap) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(QuizActivity.this);
                    alert.setTitle("Exit?");
                    alert.setMessage("Apne Relationships ke tarah quiz bhi adhura chodna chahte ho ?");
                    alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create().show();
                } else {
                    Toast.makeText(QuizActivity.this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
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
    }

}