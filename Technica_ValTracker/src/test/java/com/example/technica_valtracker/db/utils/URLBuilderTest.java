package com.example.technica_valtracker.db.utils;

import static com.example.technica_valtracker.utils.URLBuilder.*;
import static org.junit.Assert.assertEquals;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class URLBuilderTest {

    @Test
    public void buildAccountRequestUrl_whenUsernameAndTaglineAreGiven_shouldReturnFormattedString() {
        final String userName = RandomStringUtils.randomAlphabetic(10);
        final String tagLine = RandomStringUtils.randomAlphabetic(5);
        final String testUrl = String.format("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s",
                userName, tagLine);

        String url = buildAccountRequestUrL(userName, tagLine);

        assertEquals(url, testUrl);
        assertEquals(url.getClass(), testUrl.getClass());
    }

    @Test
    public void buildSummonerRequestUrl_whenPuuidAndRegionAreGiven_shouldReturnFormattedString() {
        final String puuid = RandomStringUtils.randomAlphanumeric(40);
        final String region = RandomStringUtils.randomAlphabetic(4);
        final String testUrl = String.format("https://%s.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/%s",
                region, puuid);

        String url = buildSummonerRequestUrl(puuid, region);

        assertEquals(url, testUrl);
        assertEquals(url.getClass(), testUrl.getClass());
    }

    @Test
    public void buildLeagueRequestUrl_shouldReturnString() {
        final String summonerId = RandomStringUtils.randomAlphanumeric(40);
        final String region = RandomStringUtils.randomAlphabetic(4);
        final String testUrl = String.format("https://%s.api.riotgames.com/lol/league/v4/entries/by-summoner/%s",
                region, summonerId);

        String url = buildLeagueRequestUrl(summonerId, region);

        assertEquals(url, testUrl);
        assertEquals(url.getClass(), testUrl.getClass());
    }

    @Test
    public void buildChampionRequestUrl_shouldReturnString() {
        final String puuid = RandomStringUtils.randomAlphanumeric(40);
        final String region = RandomStringUtils.randomAlphabetic(4);
        final String testUrl = String.format("https://%s.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/%s/top?count=3",
                region, puuid);

        String url = buildChampionRequestUrl(puuid, region);

        assertEquals(url, testUrl);
        assertEquals(url.getClass(), testUrl.getClass());
    }
}
