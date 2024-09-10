package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.Validation;

// For testing
import java.security.SecureRandom;


public class User {
    private String riotId;
    private String password;
    private String email;
    private Boolean isLoggedIn;
    private String userId;



    // Constructors

    // First time login Constructor. requires all details except for the PUUID which is retrieved with an
    // API call
    public User(String riotId, String password, String email) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;

        // PUUID retrieval
        //this.userID = PUUIDGet();
        // Split RiotID into two and forward this onto the API classes
        //sendRiotID();

        // For the moment though, lets randomly generate a PUUID to make the program function correctly
        this.userId = generateRandomString();

    }

    // any other time login Constructor. Strings are in a SPECIFIC ORDER
    public User(String userId, String email, String password, String riotId) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
        this.userId = userId;

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

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userid) {
        this.userId = userid;
    }

    /// Placeholder for a 'do something' after login
    public void IHaveBeenAccessed() {

        System.out.println("I have been accessed! My email is " + email);
        this.isLoggedIn = true;
    }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "User{" +
                "RiotID='" + riotId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userID='" + userId + '\'' +
                ", Is logged in ='" + isLoggedIn + '\'' +
                '}';
    }

    // API PLACERHOLDER: PUUID Get for UserID
    // PUUIDGet();{}

    // API PLACEHOLDER: Submit separated RiotID
    public void sendRiotID() {
        // Split the Riot ID into username and tagline
        String[] splitRiotID = Validation.splitRiotID(riotId);
        String userName = splitRiotID[0];
        String tagLine = splitRiotID[1]; // May be null if not provided

        System.out.println("Sending String! S1: " + userName + "  S2: " + tagLine);

        // Call the method to get account info by Riot ID
        //getAccountInfoByRiotID(userName, tagLine);
    }

    // Random
    // This is just for testing
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
    private static final int STRING_LENGTH = 88; // Based on your pattern length

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

}