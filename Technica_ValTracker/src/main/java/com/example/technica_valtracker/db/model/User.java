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
    private Boolean isLoggedIn;
    @JsonAlias("puuid")
    private String userId;
    private String region;

    // Constructors

    // First time login Constructor. requires all details except for the PUUID which is retrieved with an
    // API call
    public User(String riotId, String password, String email, String region) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
        this.region = region;

        // The PUUID get happens here. This requires more API work atm (notes at the bottom)
        // For the moment though, lets randomly generate a PUUID to make the program function correctly
        this.userId = generateRandomString();



    }

    // any other time login Constructor. Strings are in a SPECIFIC ORDER
    public User(String userId, String email, String password, String riotId, String region) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
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
                ", Region ='" + region + '\'' +
                '}';
    }

    // API STUFF
    // splits the riotID into a username and a tag, for interpretation by API
    public List<String> riotIDTagSplit() {
        // Split the Riot ID into username and tagline
        String[] splitRiotID = Validation.splitRiotID(riotId);
        String userName = splitRiotID[0];
        String tagLine = splitRiotID[1]; // May be null if not provided

        // Return both values in a List
        return Arrays.asList(userName, tagLine);

    }

    // Returns an API response given username and tagline
    public ResponseBody getAccountByRiotId(String userName, String tagLine) throws IOException {
        String json;
        String requestUrl = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + userName + "/" + tagLine;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.ANNETTE_RIOT_KEY)
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

    // Returns the PUUID based on given details
    // Note: This doesnt work yet, but does nicely join the two above methods for an easy call. This
    // method should eventually return the PUUID as a string
    private void puuidGet() throws IOException{

        List<String> usernameTagSplit = riotIDTagSplit();

        // Extract username and tag from the list
        String username = usernameTagSplit.get(0);
        String tagLine = usernameTagSplit.get(1);

        ResponseBody responseBody = getAccountByRiotId(username,tagLine);

        System.out.println("I made it!");
        System.out.println(responseBody);


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