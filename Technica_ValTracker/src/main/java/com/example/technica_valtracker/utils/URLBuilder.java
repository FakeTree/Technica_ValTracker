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

    public static String buildMatchIDsRequestUrl(String puuid, String region) {
        final String baseUrl = "api.riotgames.com/lol/match/v5/matches/by-puuid";
        final String route = getRoute(region);

        return String.format("https://%s.%s/%s/ids?start=0&count=20", route, baseUrl, puuid);
    }

    public static String buildMatchRequestUrl(String matchID, String region) {
        final String baseUrl = "api.riotgames.com/lol/match/v5/matches";
        final String route = getRoute(region);

        return String.format("https://%s.%s/%s", route, baseUrl, matchID);
    }

    /**
     * The League API endpoint uses the 'americas', 'asia', 'europe', or 'sea'
     * routing values which each cover specific region servers. This function
     * returns the appropriate routing value depending on the region server given.
     * @param region Region server
     * @return Route value as a string
     */
    public static String getRoute(String region) {
        String route = switch (region) {
            // AMERICAS route: serves NA1, BR1, LA1, LA2.
            case "br1", "na1", "la1", "la2" -> "americas";
            // ASIA route: serves KR and JP1.
            case "kr", "jp1" -> "asia";
            // EUROPE route: serves EUN1, EUW1, ME1, TR1, RU.
            case "eun1", "euw1", "me1", "tr1", "ru" -> "europe";
            // SEA route: serves OC1, PH2, SG2, TH2, TW2, VN2.
            case "oc1", "ph2", "sg2", "th2", "tw2", "vn2" -> "sea";
            default -> "";
        };

        return route;
    }
}
