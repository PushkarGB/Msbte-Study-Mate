package com.example.quizapp.Activity;

import static com.example.quizapp.Activity.PdfViewActivity.PDF_RESOURCE_ID_EXTRA;
import static com.example.quizapp.Activity.PdfViewActivity.PDF_SUB_TITLE_EXTRA;
import static com.example.quizapp.Activity.PdfViewActivity.PDF_TITLE_EXTRA;
import static com.example.quizapp.Activity.PdfViewActivity.PDF_URL_EXTRA;
import static com.example.quizapp.SingletonClasses.CourseDataManager.TYPE_MCQ;
import static com.example.quizapp.SingletonClasses.CourseDataManager.TYPE_PR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.quizapp.R;
import com.example.quizapp.SingletonClasses.PdfDataManager;

import java.util.Objects;

public class CourseHomeActivity extends AppCompatActivity {


    private String courseNm, cAbbreviation, cType;
    ImageView CourseImageView;
    ConstraintLayout opt1, opt2, opt3, opt4;

    ImageView backButton;
    TextView CourseNameView, opt1Txt, opt2Txt, opt3Txt, opt4Txt;

    public static final String COURSE_NAME_EXTRA = "com.example.quizApp.Extra.courseName";
    public static final String COURSE_ABBREVIATION_EXTRA = "com.example.quizApp.Extra.courseAbbreviation";
    public static final String COURSE_TYPE_EXTRA = "com.example.quizApp.Extra.courseType";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_course);

        courseNm = getIntent().getStringExtra(COURSE_NAME_EXTRA);
        cAbbreviation = getIntent().getStringExtra(COURSE_ABBREVIATION_EXTRA);
        cType = getIntent().getStringExtra(COURSE_TYPE_EXTRA);

        backButton = findViewById(R.id.course_backBtn);
        opt1 = findViewById(R.id.CourseHomeOpt1);
        opt2 = findViewById(R.id.CourseHomeOpt2);
        opt3 = findViewById(R.id.CourseHomeOpt3);
        opt4 = findViewById(R.id.CourseHomeOpt4);

        opt1Txt = findViewById(R.id.courseOptText1);
        opt2Txt = findViewById(R.id.courseOptText2);
        opt3Txt = findViewById(R.id.courseOptText3);
        opt4Txt = findViewById(R.id.courseOptText4);

        CourseNameView = findViewById(R.id.courseNameView);
        CourseImageView = findViewById(R.id.courseIcon);


        CourseNameView.setText(courseNm);


        int drawableResourceId = getResources().getIdentifier(cAbbreviation, "drawable", getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(CourseImageView);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Objects.equals(cType, TYPE_MCQ)) {
            changeAppearanceForMCQ();
        } else if (Objects.equals(cType, TYPE_PR)) {
            changeAppearanceForPR();
        } else {

            opt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CourseHomeActivity.this, UnitListActivity.class);
                    intent.putExtra(COURSE_ABBREVIATION_EXTRA, cAbbreviation.toLowerCase());
                    intent.putExtra(COURSE_NAME_EXTRA, courseNm);
                    startActivity(intent);
                }
            });

            opt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(cAbbreviation, "papers", courseNm, opt2Txt.getText().toString());
                }
            });
            opt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(cAbbreviation, "notes", courseNm, opt3Txt.getText().toString());
                }
            });
            opt4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(cAbbreviation, "syllabus", courseNm, opt4Txt.getText().toString());
                }
            });
        }
    }

    private void changeAppearanceForPR() {

        opt1Txt.setText("Sample Material");
        opt2.setVisibility(View.GONE);
        opt3.setVisibility(View.GONE);
        opt4Txt.setText("Syllabus");

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(cAbbreviation, " ", courseNm, opt3Txt.getText().toString());
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(cAbbreviation, "syllabus", courseNm, opt4Txt.getText().toString());
            }
        });
    }

    private void changeAppearanceForMCQ() {
        opt1Txt.setText("Practice MCQs");
        opt2Txt.setText("MCQs");
        opt3Txt.setText("Notes");
        opt4Txt.setText("Syllabus & Manual");

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CourseHomeActivity.this, UnitListActivity.class);
                i.putExtra(COURSE_ABBREVIATION_EXTRA, cAbbreviation);
                i.putExtra(COURSE_NAME_EXTRA, courseNm);
                startActivity(i);
            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(cAbbreviation, "mcq", courseNm, opt2Txt.getText().toString());
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(cAbbreviation, "notes", courseNm, opt3Txt.getText().toString());
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(cAbbreviation, "syllabus", courseNm, opt4Txt.getText().toString());
            }
        });

    }

    private void openPdf(String courseKey, String unitKey, String title, String SubTitle) {
        Intent intent = new Intent(this, PdfViewActivity.class);
        String pdfUrl = PdfDataManager.getInstance().getPdfUrl(courseKey.toLowerCase(), unitKey);
        intent.putExtra(PDF_URL_EXTRA, pdfUrl);
        intent.putExtra(PDF_TITLE_EXTRA, title);
        intent.putExtra(PDF_SUB_TITLE_EXTRA, SubTitle);
        intent.putExtra(PDF_RESOURCE_ID_EXTRA, courseKey);
        startActivity(intent);
    }
}