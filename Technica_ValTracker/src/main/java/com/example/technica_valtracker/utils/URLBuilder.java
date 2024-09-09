package com.example.technica_valtracker.utils;

import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.db.model.Summoner;

/**
 * Collection of functions that create API request URLS with the
 * appropriate query parameters.
 */
public class URLBuilder {

    public String buildSummonerQueryURL(Summoner summoner) {
        String region = summoner.getRegion();
        String puuid = summoner.getPuuid();

        return "https://" + region + Constants.SUMMONERV4_BYPUUID_LINK + puuid;
    }
}
