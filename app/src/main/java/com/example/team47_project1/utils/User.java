package com.example.team47_project1.utils;

public class User {
    public String username;
    public String getUsername() {
        if (this.username == null) {
            return "No Name";
        }
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public User(String username) {
        this.username = username;
    }
}
