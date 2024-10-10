package com.example.technica_valtracker.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

// TODO TESTING ONLY REMOVE LATER
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.*;
import com.example.technica_valtracker.UserManager;



@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String riotId;
    private String password;
    private String email;

    private Boolean isLoggedIn;
    @JsonAlias("puuid")
    private String userId;
    private String region;


    // New field for storing friends' email addresses as strings.
    // This was done for simplicity on injection
    private List<String> friends;

    // Constructors
    // any other time login Constructor. Strings are in a SPECIFIC ORDER
    public User(String userId, String email, String password, String riotId, String region, String friendsString) {
        this.riotId = riotId;
        this.password = password;
        this.email = email;
        this.userId = userId;
        this.region = region;

        // Method to pass friends
        this.friends = parseFriends(friendsString);

        // Test
        System.out.println("Friends: " + this.friends);


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

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public List<String> getFriends() {
        validateAndCleanFriends();
        return friends; }

    public void setFriends(List<String> friends) { this.friends = friends; }




    // Method to add a friend
    public void addFriend(String email) {
        if (!friends.contains(email)) {
            friends.add(email);
            // Notify the UserManager to update the database
            UserManager.getInstance().updateUserFriends(this);
        } }

    // Method to remove a friend
    public void removeFriend(String email) {
        if (friends.contains(email)) {
            friends.remove(email);
            // Notify the UserManager to update the database
            UserManager.getInstance().updateUserFriends(this);
        }
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

    // Method to parse the friends string into a list
    private List<String> parseFriends(String friendsString) {
        // No friends
        if (friendsString == null || friendsString.isEmpty()) {
            return new ArrayList<>();
        }
        // Assuming friendsString is a comma-separated string of emails
        String[] friendsArray = friendsString.split(",");
        return new ArrayList<>(Arrays.asList(friendsArray));
    }

    // Method to make sure that all friends exist in the program
    // This was made so that there was an automated way of removing friends who may have deleted their
    // account but are still contained as a friend in another account
    public void validateAndCleanFriends() {
        List<String> validFriends = new ArrayList<>();
        for (String friendEmail : friends) {
            // Check if the friend exists using UserManager
            if (UserManager.getInstance().getUserByEmail(friendEmail) != null) {
                validFriends.add(friendEmail);
            } else {
                System.out.println("Friend with email " + friendEmail + " does not exist and will be removed.");
            }
        }

        // If any friends were removed, update the friends list and persist changes
        if (validFriends.size() != friends.size()) {
            friends = validFriends;
            UserManager.getInstance().updateUserFriends(this);
        }
    }

}