package org.example.gym_web_app.dto;

public class AuthenticationRequest {

    private String username;
    private String password;

    // Default constructor
    public AuthenticationRequest() {}

    // Parameterized constructor
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
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
}