package com.example.technica_valtracker.db;

public class User {
    private int userId;
    private String email;       // OR private String username
    private String password;    // look into password hashing

    public User() {}            // modify constructor as needed...

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId  = userId;  }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
