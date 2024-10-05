package com.example.technica_valtracker;


//THIS CLASS IS USED TO PROVIDE THE API KEY FOR RIOT API, THIS KEY MUST BE REPLACED EVERY 24 HOURS, AS PER RIOT's API TERMS



public class Constants {
    //replace string below with your api key
    public static String RIOT_API_KEY = "RGAPI-7961a5e1-81e4-4b07-962a-feba42b51052";
    public static String[] requestHeaders = new String[] { "X-Riot-Token", RIOT_API_KEY };
}