package com.example.quizapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.quizapp.R;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView;
    private ImageView thumbnailImageView;
    private Button viewDocumentButton;;

    public static final String EXTRA_NEWS_TITLE = "newsTitle";
    public static final String EXTRA_NEWS_DESCRIPTION = "newsDescription";
    public static final String EXTRA_NEWS_IMAGE_URL = "newsImageUrl";
    public static final String EXTRA_NEWS_ARTICLE_URL = "newsArticleUrl";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // Initializing views
        titleTextView = findViewById(R.id.newsTitleTextView);
        descriptionTextView = findViewById(R.id.newsDescriptionTextView);
        thumbnailImageView = findViewById(R.id.newsThumbnailImageView);
        viewDocumentButton = findViewById(R.id.viewDocumentBtn);

        // Getting data from intent
        String title = getIntent().getStringExtra("newsTitle");
        String description = getIntent().getStringExtra("newsDescription");
        String imageUrl = getIntent().getStringExtra("newsImageUrl");
        String articleUrl = getIntent().getStringExtra("newsArticleUrl");

        // Setting data to views
        titleTextView.setText(title);
        descriptionTextView.setText(description);

        // Load thumbnail image using Glide
        Glide.with(this)
                .load(imageUrl)
                .into(thumbnailImageView);

        if (articleUrl != null) {
            viewDocumentButton.setOnClickListener(v -> openDocument(articleUrl));

        }
    }
    private void  openDocument(String articleUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(articleUrl), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("NewsDetailActivity", "Error opening document: " + e.getMessage());
        }
    }
}
