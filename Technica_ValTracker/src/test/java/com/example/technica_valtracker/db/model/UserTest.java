package com.example.technica_valtracker.db.model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.technica_valtracker.db.model.User;



public class UserTest {
    User testUser = new User("puuid", "email", "Password", "riotID", "selectedRegion");



    // Test that the password is hashed
    @Test
    void testMyMethod() {
        int actual;
        int expected;
        assertEquals(expected, actual);
    }


}
