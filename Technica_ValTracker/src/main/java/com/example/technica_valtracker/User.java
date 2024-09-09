package com.example.technica_valtracker;

public class User {
    private String riotid;
    private String password;
    private String email;
    private Boolean isLoggedIn;

    // Constructor
    public User(String riotid, String password, String email) {
        this.riotid = riotid;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
    }

    // Getters and Setters
    public String getRiotID() {
        return riotid;
    }
    public void setRiotID(String riotid) {
        this.riotid = riotid;
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
                "RiotID='" + riotid + '\'' +
                ", email='" + email + '\'' +
                ", Is logged in ='" + isLoggedIn + '\'' +
                '}';
    }
}