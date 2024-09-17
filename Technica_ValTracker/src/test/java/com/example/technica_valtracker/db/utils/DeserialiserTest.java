package com.example.technica_valtracker.db.utils;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.Champion;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.technica_valtracker.utils.Deserialiser.*;
import static org.junit.Assert.assertEquals;

public class DeserialiserTest {
    Random rand = new Random();

    @Test
    public void getErrorMessageFromJson_whenValidJsonStringIsProvided_shouldReturnErrorObjectWithDeserialisedStatusCodeAndDetail() throws JsonProcessingException {
        // Get random status code and corresponding detail
        final List<Integer> statusCodes = Arrays.asList(400, 401, 403, 404, 405, 415, 429, 500, 502, 503, 504);

        final int status = statusCodes.get(rand.nextInt(statusCodes.size()));
        final String detail = ErrorMessage.getErrorReason(status);
        final ErrorMessage testError = new ErrorMessage(status, detail);
        final String json = String.format("{\"status\":%d,\"detail\":\"%s\"}", status, detail);

        ErrorMessage err = getErrorMessageFromJson(json);

        assertEquals(err.getClass(), testError.getClass());
        assertEquals(err.getStatus(), testError.getStatus());
        assertEquals(err.getDetail(), testError.getDetail());
    }

    @Test
    public void getSummonerFromJson_whenValidJsonStringAndSummonerIsProvided_shouldUpdatedProvidedSummonerWithDeserialisedValues() throws JsonProcessingException {
        final String id = RandomStringUtils.randomAlphanumeric(40);
        final String accountId = RandomStringUtils.randomAlphanumeric(40);
        final String puuid = RandomStringUtils.randomAlphanumeric(40);
        final int profileIconId = Integer.parseInt(RandomStringUtils.randomNumeric(4));
        final long revisionDate = Long.parseLong(RandomStringUtils.randomNumeric(12));
        final int summonerLevel = Integer.parseInt(RandomStringUtils.randomNumeric(4));

        final String json = String.format("{\"id\":\"%s\",\"accountId\":\"%s\",\"puuid\":\"%s\",\"profileIconId\":%d,\"revisionDate\":%d,\"summonerLevel\":%d}",
                id, accountId, puuid, profileIconId, revisionDate, summonerLevel);

        Summoner summoner = new Summoner();

        getSummonerFromJson(json, summoner);

        assertEquals(summoner.getSummonerId(), id);
        assertEquals(summoner.getAccountId(), accountId);
        assertEquals(summoner.getPuuid(), puuid);
        assertEquals(summoner.getProfileIconId(), profileIconId);
        assertEquals(summoner.getRevisionDate(), revisionDate);
        assertEquals(summoner.getSummonerLevel(), summonerLevel);
    }

    @Test
    public void getLeagueArrayFromJson_whenValidJsonStringIsProvided_shouldReturnArrayOfLeagueObjectsWithDeserialisedValues() throws JsonProcessingException {
        final List<String> queueTypes = Arrays.asList("RANKED_SOLO_5x5", "RANKED_FLEX_SR");
        final List<String> tiers = Arrays.asList("Iron", "Bronze", "Silver", "Gold", "Platinum", "Emerald", "Diamond",
                "Master", "Grandmaster", "Challenger");
        final List<String> ranks = Arrays.asList("I", "II", "III", "IV");

        final String leagueId = RandomStringUtils.randomAlphanumeric(40);
        final String queueType = queueTypes.get(rand.nextInt(queueTypes.size()));
        final String tier = tiers.get(rand.nextInt(tiers.size()));
        final String rank = ranks.get(rand.nextInt(ranks.size()));

        final String json = String.format("[{\"leagueId\":\"%s\",\"queueType\":\"%s\",\"tier\":\"%s\",\"rank\":\"%s\"}]",
                leagueId, queueType, tier, rank);

        League[] leagues = getLeagueArrayFromJson(json);

        assertEquals(leagues[0].getLeagueId(), leagueId);
        assertEquals(leagues[0].getQueueType(), queueType);
        assertEquals(leagues[0].getTier(), tier);
        assertEquals(leagues[0].getRank(), rank);
    }

    @Test
    public void getChampionArrayFromJson_whenValidJsonStringIsProvided_ReturnArrayOfChampionObjectsWithDeserialisedValues() throws JsonProcessingException {
        final long championId = Integer.parseInt(RandomStringUtils.randomNumeric(3));
        final int championPoints = Integer.parseInt(RandomStringUtils.randomNumeric(6));

        final String json = String.format("[{\"championId\":%d,\"championPoints\":%d}]",
                championId, championPoints);

        Champion[] champions = getChampionArrayFromJson(json);

        assertEquals(champions[0].getChampionId(), championId);
        assertEquals(champions[0].getChampionPoints(), championPoints);
    }
}
