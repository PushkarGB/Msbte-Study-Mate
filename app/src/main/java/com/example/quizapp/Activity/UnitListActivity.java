package com.example.quizapp.Activity;

import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_ABBREVIATION_EXTRA;
import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_NAME_EXTRA;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Adapter.UnitAdapter;
import com.example.quizapp.Model.Units;
import com.example.quizapp.R;
import com.example.quizapp.SingletonClasses.UnitDataManager;

import java.util.ArrayList;

public class UnitListActivity extends AppCompatActivity {

    String courseAbbreviation, courseName;
    private RecyclerView.Adapter unitAdapter;
    private RecyclerView unitRecycleView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unit_list);

        ImageView backBtn = findViewById(R.id.unitBackBtn);
        TextView courseNameView = findViewById(R.id.unitListCourseName);

        courseName = getIntent().getStringExtra(COURSE_NAME_EXTRA);
        courseAbbreviation = getIntent().getStringExtra(COURSE_ABBREVIATION_EXTRA);

        if ((courseAbbreviation != null) && (courseName != null)) {
            // Proceed with using courseName and courseAbbreviation
            courseAbbreviation = courseAbbreviation.toLowerCase();
            courseNameView.setText(courseName);
            initRecyclerView();
        } else {
            // Handle the case where extras are missing (e.g., log an error, show a message)
            Toast.makeText(this, "Missing extras", Toast.LENGTH_SHORT).show();
            finish();
        }

        backBtn.setOnClickListener(v -> {
            finish();
        });

    }


    private void initRecyclerView() {

        ArrayList<Units> units = UnitDataManager.getInstance().getUnits(courseAbbreviation);
        if (units != null) {
            unitRecycleView = findViewById(R.id.unitsRecyclerView);
            unitRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            unitAdapter = new UnitAdapter(units, courseAbbreviation, this);
            unitRecycleView.setAdapter(unitAdapter);
        }
        else{
            Toast.makeText(this,"Coming soon..",Toast.LENGTH_SHORT).show();
            finish();
        }


    }


}