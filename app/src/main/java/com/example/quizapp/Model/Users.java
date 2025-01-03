package com.example.quizapp.Model;

public class Users {
    String userId;
    String userName;
    String profilePic;
    String email;
    String password;
    String rePassword;

    public Users(){
    }

    public Users(String id, String userName, String profilePic, String email, String password, String rePassword) {
        this.userId = id;
        this.userName = userName;
        this.profilePic = profilePic;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

}
