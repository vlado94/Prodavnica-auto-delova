package com.example.olja.carpartshop.user;

public class User {

    private String Email;
    private String Password;
    private String FirebaseToken;

    public User(){

    }

    public User(String email, String password, String firebaseToken) {
        Email = email;
        Password = password;
        FirebaseToken = firebaseToken;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirebaseToken() {
        return FirebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        FirebaseToken = firebaseToken;
    }
}
