package com.example.quizapp.Model;

public class News {
    private String title;
    private String description;
    private String imageUrl;
    private String articleUrl;

    // Default constructor required for calls to DataSnapshot.getValue(News.class)
    public News() {
    }

    public News(String title, String description, String imageUrl, String articleUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}
