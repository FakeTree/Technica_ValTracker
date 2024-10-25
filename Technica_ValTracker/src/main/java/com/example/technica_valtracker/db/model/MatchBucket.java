package com.example.technica_valtracker.db.model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code MatchBucket} class stores information about matches, including match IDs, statistics such as KDA (Kills/Deaths/Assists),
 * CS per minute (Creep Score per minute), win rate, and gold earned per minute.
 * It also provides methods to add matches and compute averages of these statistics across all games.
 */
public class MatchBucket {

    private Map<String, Match> matchArray = new HashMap<>();
    private List<String> matchIds = new ArrayList<>();
    private List<Double> AllKDA = new ArrayList<>();
    private List<Double> AllCSpMin = new ArrayList<>();
    private int AllWinRate;
    private List<Double> GoldPerMin = new ArrayList<>();

    /**
     * Clears all lists.
     */
    public void resetBucket() {
        matchArray.clear();
        matchIds.clear();
        AllKDA.clear();
        AllCSpMin.clear();
        GoldPerMin.clear();
        AllWinRate = 0;
    }

    /**
     * Adds a match to the match array.
     *
     * @param matchID the match ID.
     * @param match   the match object.
     */
    public void addMatch(String matchID, Match match) {
        matchArray.put(matchID, match);
    }

    /**
     * Retrieves the map of matches stored in the match bucket.
     *
     * @return a map containing match IDs as keys and match objects as values.
     */
    public Map<String, Match> getMatchArray() {
        return matchArray;
    }

    /**
     * Adds a KDA (Kills/Deaths/Assists) value to the list of KDA statistics.
     *
     * @param value the KDA value to add.
     */
    public void addKDA(double value) {
        AllKDA.add(value);
    }

    /**
     * Calculates the average KDA across all games.
     *
     * @return the average KDA, or 0 if no KDA values are present.
     */
    public double getKDAAcrossAllGames() {
        double sum = 0;
        for (double value : AllKDA) {
            sum += value;
        }
        return AllKDA.isEmpty() ? 0 : sum / AllKDA.size();
    }

    /**
     * Gets the list of KDA values across all games.
     *
     * @return the list of KDA values.
     */
    public List<Double> getKDA() {
        return AllKDA;
    }

    /**
     * Adds a Creep Score per minute (CSpMin) value to the list.
     *
     * @param value the CSpMin value to add.
     */
    public void addCSpMin(double value) {
        AllCSpMin.add(value);
    }

    /**
     * Calculates the average Creep Score per minute (CSpMin) across all games.
     *
     * @return the average CSpMin, or 0 if no values are present.
     */
    public double getCSpMinAcrossAllGames() {
        double sum = 0;
        for (double value : AllCSpMin) {
            sum += value;
        }
        return AllCSpMin.isEmpty() ? 0 : sum / AllCSpMin.size();
    }

    /**
     * Gets the list of Creep Score per minute (CSpMin) values across all games.
     *
     * @return the list of CSpMin values.
     */
    public List<Double> getCSpMin() {
        return AllCSpMin;
    }

    /**
     * Increments the win count for the match bucket.
     */
    public void addWinRate() {
        AllWinRate += 1;
    }

    /**
     * Calculates the win rate across all games.
     *
     * @return the win rate as a percentage.
     */
    public double getWinRateAcrossAllGames() {
        if (AllWinRate == 0) {
            return 0.0;
        }
        return (double) AllWinRate / matchArray.size();
    }

    /**
     * Parses a JSON response string to extract match IDs and store them in the match bucket.
     *
     * @param res the JSON response containing match IDs.
     * @throws IOException if there is an error during parsing.
     */
    public void setMatchListByPUUID(String res) throws IOException {
        emptyMatchId();
        Matcher m = Pattern.compile("[A-Z0-9_]+").matcher(res);
        while (m.find()) {
            addMatchIds(m.group());
        }
    }

    /**
     * Adds a gold earned per minute value to the list.
     *
     * @param value the gold per minute value to add.
     */
    public void addGoldPerMin(double value) {
        GoldPerMin.add(value);
    }

    /**
     * Calculates the average gold earned per minute across all games.
     *
     * @return the average gold per minute, or 0 if no values are present.
     */
    public double getGoldPerMinAcrossAllGames() {
        double sum = 0;
        for (double value : GoldPerMin) {
            sum += value;
        }
        return GoldPerMin.isEmpty() ? 0 : sum / GoldPerMin.size();
    }

    /**
     * Gets the list of gold earned per minute values across all games.
     *
     * @return the list of gold per minute values.
     */
    public List<Double> getAllGoldPerMin() {
        return GoldPerMin;
    }

    /**
     * Retrieves the list of match IDs stored in the match bucket.
     *
     * @return the list of match IDs.
     */
    public List<String> getMatchIds() {
        return matchIds;
    }

    /**
     * Sets the list of match IDs.
     *
     * @param param the list of match IDs to set.
     */
    public void setMatchIds(List<String> param) {
        this.matchIds = param;
    }

    /**
     * Adds a match ID to the list of match IDs.
     *
     * @param param the match ID to add.
     */
    public void addMatchIds(String param) {
        this.matchIds.add(param);
    }

    /**
     * Clears the list of match IDs.
     */
    private void emptyMatchId() {
        this.matchIds = new ArrayList<>();
    }
}
