package com.marcllort.tinder.Model;

public class UserData {

    private String login;
    private String email;
    private String password;
    private String username;

    public UserData(String username, String email, String password) {
        this.login = username;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}