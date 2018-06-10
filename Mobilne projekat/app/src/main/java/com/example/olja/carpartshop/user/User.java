package com.example.olja.carpartshop.user;

public class User {

    private String Email;
    private String Password;

    public User(){

    }

    public User(String email, String password) {
        Email = email;
        Password = password;
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
}
