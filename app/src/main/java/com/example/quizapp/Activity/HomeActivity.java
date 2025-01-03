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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.quizapp.Model.News;
import com.example.quizapp.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    /*
    In Future , When We will think to launch this app and extend its data for all branches , we will take input from the user for his branch at registration or login and accordingly change the Home page
    
    */
    //MCQ Buttons
    ConstraintLayout sub1, sub2, sub3, sub4;
    TextView showCourseList;

    ImageSlider imageSlider;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference newsRef;
    Boolean doubleTap = false;
    ArrayList<News> newsList;


    TextView tv_userName;
    CircleImageView civ_profileImage;

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
        imageSlider = findViewById(R.id.image_slider);

        auth = FirebaseAuth.getInstance();


        loadUserData();

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

        setupImageSlider();
    }

    public void setupImageSlider() {
        imageSlider.setImageList(new ArrayList<SlideModel>(), ScaleTypes.FIT);
        newsList = new ArrayList<>();
        newsRef = FirebaseDatabase.getInstance().getReference("News");

        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newsList.clear(); // Clear the list before adding new data
                ArrayList<SlideModel> slideModels = new ArrayList<>();
                int index = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    News news = data.getValue(News.class);
                    if (news != null) {
                        newsList.add(news); // Add the News object to the list
                        slideModels.add(new SlideModel(news.getThumbnailUrl(), news.getTitle(), ScaleTypes.FIT));
                    }
                }
                imageSlider.setImageList(slideModels, ScaleTypes.FIT);

                // Add a click listener to handle double-tap
                imageSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void doubleClick(int position) {
                        onNewsItemDoubleClick(position);
                    }

                    @Override
                    public void onItemSelected(int position) {
                        // Handle single click if needed
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load news: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Handles a double-click event on a news item.
     *
     * @param position The position of the clicked item in the image slider.
     */
    public void onNewsItemDoubleClick(int position) {
        // Check if the position is valid.
        if (position < 0 || position >= newsList.size()) {
            Log.e("NewsError", "onNewsItemDoubleClick: Invalid position: " + position);
            return;
        }

        // Get the selected news item from the list.
        News selectedNews = newsList.get(position);

        // Check if the news item is valid.
        if (selectedNews == null) {
            Log.e("NewsError", "onNewsItemDoubleClick: Failed to retrieve news data at position: " + position);
            return;
        }

        // Create an intent to start the NewsDetailActivity.
        Intent intent = new Intent(HomeActivity.this, NewsDetailActivity.class);

        // Add news details as extras to the intent.
        intent.putExtra(NewsDetailActivity.EXTRA_NEWS_TITLE, selectedNews.getTitle());
        intent.putExtra(NewsDetailActivity.EXTRA_NEWS_DATE, selectedNews.getDate());
        intent.putExtra(NewsDetailActivity.EXTRA_NEWS_DESCRIPTION, selectedNews.getContent());
        intent.putExtra(NewsDetailActivity.EXTRA_NEWS_IMAGE_URL, selectedNews.getThumbnailUrl());
        intent.putExtra(NewsDetailActivity.EXTRA_NEWS_ARTICLE_URL, selectedNews.getDocumentUrl());

        // Start the NewsDetailActivity.
        startActivity(intent);
    }


    private void loadUserData() {

        tv_userName = findViewById(R.id.greetUserText);
        civ_profileImage = findViewById(R.id.profileImg);
        String uid = auth.getUid();
        //read user data from real-time db and show on screen

        //sabse pehle apn lenge realtime ka refeernce
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
        userReference.orderByChild(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("userName").getValue(String.class);
                    tv_userName.setText("Hey, "+ name);
                    String imgUrl = snapshot.child("profilePic").getValue(String.class);
                    if(imgUrl != null){
                        Glide.with(HomeActivity.this).load(imgUrl).into(civ_profileImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SignUp","Something went wrong in fetching user data");
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
}