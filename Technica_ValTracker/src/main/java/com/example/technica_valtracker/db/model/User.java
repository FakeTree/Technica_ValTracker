package com.example.technica_valtracker.db.model;

public class User {
    private String riotId;
    private String password;
    private String email;
    private Boolean isLoggedIn;

    // Constructor
    public User(String riotId, String password, String email) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
    }

    // Getters and Setters
    public String getRiotID() {
        return riotId;
    }
    public void setRiotID(String riotid) {
        this.riotId = riotid;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /// Placeholder for a 'do something' after login
    public void IHaveBeenAccessed() {
        System.out.println("I have been accessed! My email is " + email);
    }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "User{" +
                "RiotID='" + riotId + '\'' +
                ", email='" + email + '\'' +
                ", Is logged in ='" + isLoggedIn + '\'' +
                '}';
    }
}