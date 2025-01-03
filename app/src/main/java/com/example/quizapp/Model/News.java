package com.example.quizapp.Model;

public class News {
    private String title;
    private String content;
    private String thumbnailUrl;
    private String documentUrl;

    private String date;

    // Default constructor required for calls to DataSnapshot.getValue(News.class)
    public News() {
    }

    public News(String content, String date , String documentUrl, String thumbnailUrl, String title) {
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.documentUrl = documentUrl;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
