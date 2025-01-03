package com.example.quizapp.Model;

public class CourseDomain {
    String title;
    String code;
    String imgPath;
    String type;

    public CourseDomain(String title, String code, String imgPath, String type) {
        this.title = title;
        this.code = code;
        this.imgPath = imgPath;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
