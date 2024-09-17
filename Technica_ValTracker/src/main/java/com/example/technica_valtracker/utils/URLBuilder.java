package com.example.technica_valtracker.utils;

/**
 * Collection of functions that construct and provide API request urls.
 */
public class URLBuilder {
    public static String buildAccountRequestUrL(String userName, String tagLine) {
        final String baseUrl = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/";
        return baseUrl + userName + "/" + tagLine;
    }

    public static String buildSummonerRequestUrl(String puuid, String region) {
        final String baseUrl = ".api.riotgames.com/lol/summoner/v4/summoners/by-puuid/";
        return "https://" + region + baseUrl + puuid;
    }

    public static String buildLeagueRequestUrl(String summonerId, String region) {
        final String baseUrl = ".api.riotgames.com/lol/league/v4/entries/by-summoner/";
        return "https://" + region + baseUrl + summonerId;
    }

    public static String buildChampionRequestUrl(String puuid, String region) {
        final String baseUrl = ".api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/";
        return "https://" + region + baseUrl + puuid + "/top?count=3";
    }
}
