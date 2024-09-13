package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.Validation;

// For testing
import java.io.IOException;
import java.security.SecureRandom;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import com.fasterxml.jackson.annotation.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String riotId;
    private String password;
    private String email;
    @JsonAlias("puuid")
    private String userId;
    private String region;

    // Constructors
    // any other time login Constructor. Strings are in a SPECIFIC ORDER
    public User(String userId, String email, String password, String riotId, String region) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.userId = userId;
        this.region = region;

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

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
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
                ", password='" + password + '\'' +
                ", userID='" + userId + '\'' +
                ", Region ='" + region + '\'' +
                '}';
    }




    // Random
    // This is just for testing!
    // When the user is constructed for the first time, the RiotID is known but the PUUID (userID) is not
    // So the plan is to construct the user initially with what is know (email, region, riotID and password
    // and make all the appropriate API calls to populate the userID a moment later
    // However until this works, a random one is generated below instead to make the program work
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