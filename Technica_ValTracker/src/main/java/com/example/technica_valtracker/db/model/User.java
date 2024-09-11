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

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String riotId;
    private String password;
    private String email;
    private Boolean isLoggedIn;
    @JsonAlias("puuid")
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

    public ResponseBody getAccountByRiotId(String userName, String tagLine) throws IOException {
        String json;
        String requestUrl = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + userName + "/" + tagLine;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.RIOT_API_KEY)
                .url(requestUrl)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // Parse successful response as string
            json = response.body().string();
        }

        return new ResponseBody(json, false);
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