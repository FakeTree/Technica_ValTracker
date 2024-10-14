package com.example.technica_valtracker.db.model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchBucket {
    private Map<String, Match> matchArray = new HashMap<String, Match>();
    private List<String> matchIds = new ArrayList<String>();
    private List<Double> AllKDA = new ArrayList<>();
    private List<Double> AllCSpMin = new ArrayList<>();
    private int AllWinRate;
    private List<Double> GoldPerMin = new ArrayList<>();

    public void addMatch(String matchID, Match match) {matchArray.put(matchID, match);}
    public Map<String, Match> getMatchArray() {return matchArray;}

    // Method to add a double value
    public void addKDA(double value) {
        AllKDA.add(value);
    }
    public double getKDAAcrossAllGames(){
        double sum = 0;
        for (double value : AllKDA) {
            sum += value;
        }
        return AllKDA.isEmpty() ? 0 : sum / AllKDA.size();
    }
    // Getter for values list
    public List<Double> getKDA() {
        return AllKDA;
    }// Method to add a double value
    public void addCSpMin(double value) {
        AllCSpMin.add(value);
    }
    public double getCSpMinAcrossAllGames(){
        double sum = 0;
        for (double value : AllCSpMin) {
            sum += value;
        }
        return AllCSpMin.isEmpty() ? 0 : sum / AllCSpMin.size();
    }
    // Getter for values list
    public List<Double> getCSpMin() {
        return AllCSpMin;
    }

    // Method to add a double value
    public void addWinRate() {
        AllWinRate +=1;
    }
    public double getWinRateAcrossAllGames(){
        return (double) AllWinRate /matchArray.size();
    }

    public void setMatchListByPUUID(String res) throws IOException {
        emptyMatchId();

        Matcher m = Pattern.compile("[A-Z0-9_]+").matcher(res);
        while(m.find()){
            addMatchIds(m.group());
        }
    }

    // Method to add a double value
    public void addGoldPerMin(double value) {
        GoldPerMin.add(value);
    }
    public double getGoldPerMinAcrossAllGames(){
        double sum = 0;
        for (double value : GoldPerMin) {
            sum += value;
        }
        return GoldPerMin.isEmpty() ? 0 : sum / GoldPerMin.size();
    }
    // Getter for values list
    public List<Double> getAllGoldPerMin() {
        return GoldPerMin;
    }

    public List<String> getMatchIds(){ return matchIds; }
    public void setMatchIds (List<String> param){ this.matchIds = param; }
    public void addMatchIds (String param){ this.matchIds.add(param); }
    private void emptyMatchId(){ this.matchIds = new ArrayList<String>(); }
}
