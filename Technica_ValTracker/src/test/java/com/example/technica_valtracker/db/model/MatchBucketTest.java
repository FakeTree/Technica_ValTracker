package com.example.technica_valtracker.db.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchBucketTest {

    private MatchBucket matchBucket;
    private Match match;

    @BeforeEach
    void setUp() {
        // Initialize MatchBucket and mock Match object
        matchBucket = new MatchBucket();
        match = new Match();
    }

    @Test
    void testAddMatchAndRetrieve() {
        // Test adding a match and retrieving it
        matchBucket.addMatch("match1", match);
        assertEquals(1, matchBucket.getMatchArray().size(), "Match array should contain 1 match.");
        assertEquals(match, matchBucket.getMatchArray().get("match1"));
    }

    @Test
    void testAddKDAAndCalculateAverage() {
        // Test adding KDA values and calculating the average
        matchBucket.addKDA(3.5);
        matchBucket.addKDA(2.0);
        matchBucket.addKDA(4.0);

        double averageKDA = matchBucket.getKDAAcrossAllGames();
        assertEquals(3.17, averageKDA, 0.01, "Average KDA should be calculated correctly.");
    }

    @Test
    void testAddCSpMinAndCalculateAverage() {
        // Test adding CSpMin values and calculating the average
        matchBucket.addCSpMin(5.5);
        matchBucket.addCSpMin(6.2);
        matchBucket.addCSpMin(7.0);

        double averageCSpMin = matchBucket.getCSpMinAcrossAllGames();
        assertEquals(6.23, averageCSpMin, 0.01, "Average CSpMin should be calculated correctly.");
    }

    @Test
    void testAddGoldPerMinAndCalculateAverage() {
        // Test adding gold per minute values and calculating the average
        matchBucket.addGoldPerMin(300.5);
        matchBucket.addGoldPerMin(350.0);
        matchBucket.addGoldPerMin(275.75);

        double averageGoldPerMin = matchBucket.getGoldPerMinAcrossAllGames();
        assertEquals(308.75, averageGoldPerMin, 0.01, "Average Gold per minute should be calculated correctly.");
    }

    @Test
    void testAddWinRateAndCalculate() {
        // Test adding win rate and calculating the percentage
        matchBucket.addMatch("match1", match);
        matchBucket.addMatch("match2", match);
        matchBucket.addMatch("match3", match);

        // Simulate 2 wins
        matchBucket.addWinRate();
        matchBucket.addWinRate();

        double winRate = matchBucket.getWinRateAcrossAllGames();
        assertEquals(0.67, winRate, 0.01, "Win rate should be calculated correctly (2 wins out of 3 matches).");
    }

    @Test
    void testSetMatchListByPUUID() throws IOException {
        // Simulate a JSON response with match IDs and test parsing
        String jsonResponse = "[\"MATCH_ID_123\", \"MATCH_ID_456\", \"MATCH_ID_789\"]";

        matchBucket.setMatchListByPUUID(jsonResponse);

        List<String> matchIds = matchBucket.getMatchIds();
        assertEquals(3, matchIds.size(), "There should be 3 match IDs parsed from the JSON.");
        assertTrue(matchIds.contains("MATCH_ID_123"));
        assertTrue(matchIds.contains("MATCH_ID_456"));
        assertTrue(matchIds.contains("MATCH_ID_789"));
    }

    @Test
    void testResetBucket() {
        // Test that the bucket is correctly reset
        matchBucket.addMatch("match1", match);
        matchBucket.addKDA(3.5);
        matchBucket.addCSpMin(5.5);
        matchBucket.addGoldPerMin(300.5);
        matchBucket.addWinRate();

        matchBucket.resetBucket();

        assertTrue(matchBucket.getMatchArray().isEmpty(), "Match array should be empty after reset.");
        assertTrue(matchBucket.getKDA().isEmpty(), "KDA list should be empty after reset.");
        assertTrue(matchBucket.getCSpMin().isEmpty(), "CSpMin list should be empty after reset.");
        assertTrue(matchBucket.getAllGoldPerMin().isEmpty(), "Gold per minute list should be empty after reset.");
        assertEquals(0, matchBucket.getWinRateAcrossAllGames(), "Win rate should be 0 after reset.");
    }
}
