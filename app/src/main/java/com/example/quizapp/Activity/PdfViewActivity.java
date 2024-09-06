package com.example.quizapp.Activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.quizapp.R;

public class PdfViewActivity extends AppCompatActivity {

    public static final String PDF_URL_EXTRA = "com.example.quizApp.PDF.Extra.URL";
    public static final String PDF_TITLE_EXTRA = "com.example.quizApp.PDF.Extra.Title";
    public static final String PDF_SUB_TITLE_EXTRA = "com.example.quizApp.PDF.Extra.SubTitle";
    public static final String PDF_RESOURCE_ID_EXTRA = "com.example.quizApp.PDF.Extra.ResourceID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        String title = getIntent().getStringExtra(PDF_TITLE_EXTRA);
        String subTitle = getIntent().getStringExtra(PDF_SUB_TITLE_EXTRA);
        String resourceIDStr = getIntent().getStringExtra(PDF_RESOURCE_ID_EXTRA);
        String pdfUrl = getIntent().getStringExtra(PDF_URL_EXTRA);

        ImageView pdfTitleImg = findViewById(R.id.pdfTitleImg);
        TextView pdfTitle,pdfSubTitle;
        pdfTitle = findViewById(R.id.pdfTitle);
        pdfSubTitle = findViewById(R.id.pdfSubTitle);

        int resourceID = pdfTitleImg.getResources().getIdentifier(resourceIDStr, "drawable", getPackageName());  //return 0 if no resource file found

        if(resourceID != 0) {
            Glide.with(this).load(resourceID).into(pdfTitleImg);
        }
        pdfTitle.setText(title);
        pdfSubTitle.setText(subTitle);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable zoom controls on the WebView
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(pdfUrl);
    }
}