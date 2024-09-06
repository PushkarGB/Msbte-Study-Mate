package com.example.quizapp.Activity;

import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_ABBREVIATION_EXTRA;
import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_NAME_EXTRA;
import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_TYPE_EXTRA;
import static com.example.quizapp.SingletonClasses.CourseDataManager.TYPE_MCQ;
import static com.example.quizapp.SingletonClasses.CourseDataManager.TYPE_PR;
import static com.example.quizapp.SingletonClasses.CourseDataManager.TYPE_THEORY;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.quizapp.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    /*
    In Future , When We will think to launch this app and extend its data for all branches , we will take input from the user for his branch at registration or login and accordingly change the Home page
    
    */
    //MCQ Buttons
    ConstraintLayout sub1, sub2, sub3, sub4;
    TextView showCourseList;

    FirebaseAuth auth;
    Boolean doubleTap = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sub1 = findViewById(R.id.AJP);
        sub2 = findViewById(R.id.EST);
        sub3 = findViewById(R.id.ETI);
        sub4 = findViewById(R.id.MGT);
        showCourseList = findViewById(R.id.courseListBtn);

        auth = FirebaseAuth.getInstance();

        View.OnClickListener subClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subject, abbr;

                if (R.id.AJP == v.getId()) {
                    subject = "Advance Java Programming";
                    abbr = "AJP";
                } else if (R.id.EST == v.getId()) {
                    subject = "Environmental Studies";
                    abbr = "est";
                } else if (R.id.ETI == v.getId()) {
                    subject = "Emerging Trends in Information Technology";
                    abbr = "eti";
                } else if (R.id.MGT == v.getId()) {
                    subject = "Management";
                    abbr = "mgt";
                } else {
                    subject = "Advance Java Programming";
                    abbr = "ajp";
                }

                Intent i = new Intent(HomeActivity.this, UnitListActivity.class);
                i.putExtra(COURSE_ABBREVIATION_EXTRA, abbr);
                i.putExtra(COURSE_NAME_EXTRA, subject);
                startActivity(i);
            }
        };

        sub1.setOnClickListener(subClickListener);
        sub2.setOnClickListener(subClickListener);
        sub3.setOnClickListener(subClickListener);
        sub4.setOnClickListener(subClickListener);

        showCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SemesterListActivity.class);
                startActivity(intent);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (doubleTap) {
                    finishAffinity();
                } else {
                    Toast.makeText(HomeActivity.this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
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

        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();// Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(new SlideModel(R.drawable.osy, "Operating Systems",null));
        imageList.add(new SlideModel(R.drawable.css, "Client Side Scripting",null));
        imageList.add(new SlideModel(R.drawable.ste, "Software Testing",null));

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

            }
            @Override
            public void doubleClick(int i) {
                Intent intent = new Intent(HomeActivity.this, CourseHomeActivity.class);
                switch (i) {
                    case 0:
                        intent.putExtra(COURSE_NAME_EXTRA, imageList.get(0).getTitle());
                        intent.putExtra(COURSE_ABBREVIATION_EXTRA, "osy");
                        intent.putExtra(COURSE_TYPE_EXTRA, TYPE_THEORY);
                    case 1:
                        intent.putExtra(COURSE_NAME_EXTRA, imageList.get(0).getTitle());
                        intent.putExtra(COURSE_ABBREVIATION_EXTRA, "ste");
                        intent.putExtra(COURSE_TYPE_EXTRA, TYPE_THEORY);
                    case 2:
                        intent.putExtra(COURSE_NAME_EXTRA, imageList.get(0).getTitle());
                        intent.putExtra(COURSE_ABBREVIATION_EXTRA, "css");
                        intent.putExtra(COURSE_TYPE_EXTRA, TYPE_THEORY);
                    case 3:
                        intent.putExtra(COURSE_NAME_EXTRA, imageList.get(0).getTitle());
                        intent.putExtra(COURSE_ABBREVIATION_EXTRA, "ajp");
                        intent.putExtra(COURSE_TYPE_EXTRA, TYPE_MCQ);
                }
                startActivity(intent);
            }
        });

    }

    public void logout(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout?");
        alert.setMessage("Mat kar bhai,ro dunga\uD83E\uDD79");
        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).create().show();
    }

    public void clicked(View view) {
        Toast.makeText(this, "Home was clicked", Toast.LENGTH_SHORT).show();
    }
}