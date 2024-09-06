package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapp.R;

import java.sql.Struct;

public class SemesterListActivity extends AppCompatActivity {

    public static final String SEMESTER_EXTRA = "com.example.quizApp.Semester";
    ImageView sem1, sem2, sem3, sem4, sem5, sem6,backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_semester_list);

        sem1 = findViewById(R.id.sem1);
        sem2 = findViewById(R.id.sem2);
        sem3 = findViewById(R.id.sem3);
        sem4 = findViewById(R.id.sem4);
        sem5 = findViewById(R.id.sem5);
        sem6 = findViewById(R.id.sem6);
        backBtn = findViewById(R.id.sem_backBtn);

        View.OnClickListener semClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSem;
                if (R.id.sem1 == v.getId()) {
                    selectedSem = "one";
                } else if (R.id.sem2 == v.getId()) {
                    selectedSem = "two";
                } else if (R.id.sem3 == v.getId()) {
                    selectedSem = "three";
                } else if (R.id.sem4 == v.getId()) {
                    selectedSem = "four";
                } else if (R.id.sem5 == v.getId()) {
                    selectedSem = "five";
                } else if (R.id.sem6 == v.getId()) {
                    selectedSem = "six";
                }
                else {
                    selectedSem = null;
                }
                    Intent intent = new Intent(SemesterListActivity.this, CoursesListActivity.class);
                    intent.putExtra(SEMESTER_EXTRA, selectedSem);
                    startActivity(intent);
            }
        };

        sem1.setOnClickListener(semClickListener);
        sem2.setOnClickListener(semClickListener);
        sem3.setOnClickListener(semClickListener);
        sem4.setOnClickListener(semClickListener);
        sem5.setOnClickListener(semClickListener);
        sem6.setOnClickListener(semClickListener);
        backBtn.setOnClickListener(v -> {
           Intent i = new Intent(SemesterListActivity.this,HomeActivity.class);
           startActivity(i);
           finish();
        });
    }

}