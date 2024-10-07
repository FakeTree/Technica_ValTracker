package com.example.technica_valtracker.db.model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchBucket {
    private ArrayList<Match> matchArray = new ArrayList<Match>();
    private List<String> matchIds = new ArrayList<String>();
    public void setMatchArray(ArrayList<Match> matchArray) {this.matchArray = matchArray;}
    public void addMatch(String matchID, Match match) {matchArray.add(match);}
    public ArrayList<Match> getMatchArray() {return matchArray;}

    public void setMatchListByPUUID(String res) throws IOException {
        emptyMatchId();

        Matcher m = Pattern.compile("[A-Z0-9_]+").matcher(res);
        while(m.find()){
            System.out.println("Match Found");
            addMatchIds(m.group());
        }
    }

    public List<String> getMatchIds(){ return matchIds; }
    public void setMatchIds (List<String> param){ this.matchIds = param; }
    public void addMatchIds (String param){ this.matchIds.add(param); }
    private void emptyMatchId(){ this.matchIds = new ArrayList<String>(); }
}
