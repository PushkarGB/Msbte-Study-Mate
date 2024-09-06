package com.example.quizapp.Domain;

public class Users {
    String userId,userName,profilePic,mail,password,cPassword,status;
    public Users(){
    }

    public Users(String id, String name, String imageUriStr, String email, String pass, String rePass, String status) {
        this.userId = id;
        this.userName = name;
        this.profilePic = imageUriStr;
        this.mail = email;
        this.password = pass;
        this.cPassword = rePass;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
