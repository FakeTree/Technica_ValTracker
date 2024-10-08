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

    public void addMatch(String matchID, Match match) {matchArray.put(matchID, match);}
    public Map<String, Match> getMatchArray() {return matchArray;}

    // Method to add a double value
    public void addValue(double value) {
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
    public List<Double> getValues() {
        return AllKDA;
    }

    public void setMatchListByPUUID(String res) throws IOException {
        emptyMatchId();

        Matcher m = Pattern.compile("[A-Z0-9_]+").matcher(res);
        while(m.find()){
            addMatchIds(m.group());
        }
    }

    public List<String> getMatchIds(){ return matchIds; }
    public void setMatchIds (List<String> param){ this.matchIds = param; }
    public void addMatchIds (String param){ this.matchIds.add(param); }
    private void emptyMatchId(){ this.matchIds = new ArrayList<String>(); }
}
