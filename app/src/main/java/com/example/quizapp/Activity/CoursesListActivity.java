package com.example.quizapp.Activity;

import static com.example.quizapp.Activity.SemesterListActivity.SEMESTER_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Adapter.CourseAdapter;
import com.example.quizapp.Model.CourseDomain;
import com.example.quizapp.R;
import com.example.quizapp.SingletonClasses.CourseDataManager;

import java.util.ArrayList;

public class CoursesListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCoursesList;
    private RecyclerView recyclerViewCourse;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_courses_list);

        backBtn = findViewById(R.id.backImgBtn);
        String semester = getIntent().getStringExtra(SEMESTER_EXTRA);
        initRecyclerView(semester);

        backBtn.setOnClickListener(v -> {
            Intent i = new Intent(CoursesListActivity.this, SemesterListActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void initRecyclerView(String sem) {
        ArrayList<CourseDomain> courses = CourseDataManager.getInstance().getCourses(sem);
        if (courses != null) {
            recyclerViewCourse = findViewById(R.id.recyclerView);
            recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapterCoursesList = new CourseAdapter(courses);
            recyclerViewCourse.setAdapter(adapterCoursesList);
        } else {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void comingSoon() {
        Toast.makeText(this, "Under development Area!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(CoursesListActivity.this, HomeActivity.class));
            }
        }, 2000);
    }
}