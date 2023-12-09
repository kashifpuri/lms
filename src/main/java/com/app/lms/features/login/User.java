package com.app.lms.features.login;

public record User(String email, String password, String firstName, String lastName) {
    public User(String email, String password) {
        this(email,password,"","");
    }
}
