package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.utils.URLBuilder;
import kotlin.text.Regex;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import com.example.technica_valtracker.Constants;

import java.io.IOException;
import java.util.List;

import static com.example.technica_valtracker.api.Query.getQuery;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MatchTest {

    MockWebServer server;

    @Test
    // Test for Incorrect query data
    public void getMatchsByPuuid_whenResponseBodyHas400ErrorStatusCode_shouldReturnResponseBodyWithErrorCodeAndDetail() throws IOException {
        server = new MockWebServer();
        server.start();
        HttpUrl requestUrl = server.url("/lol/league/v4/entries/by-summoner/");
        server.enqueue(new MockResponse().setResponseCode(400)
                .setBody("{\"status\": {\"message\":\"Data not found\",\"status_code\":400}"));

        Match match = new Match();
        ErrorMessage error = new ErrorMessage(400, "API fetch error: Bad request");
        ResponseBody body = new ResponseBody(error);

        String[] reqHeaders = new String[]{"X-Riot-Token", "API_KEY"};

        ResponseBody Matches = getQuery(URLBuilder.buildMatchRequestUrl("puuid", "region"), Constants.requestHeaders);
        System.out.println(URLBuilder.buildMatchRequestUrl("puuid", "region"));

        assertEquals(Matches.getMessage().getStatus(), body.getMessage().getStatus());
        assertEquals(Matches.getMessage().getDetail(), body.getMessage().getDetail());

        server.shutdown();
    }

    @Test
    // Test out of date queries
    public void getMatchsByPuuid_whenResponseBodyHas403ErrorStatusCode_shouldReturnResponseBodyWithErrorCodeAndDetail() throws IOException {
        server = new MockWebServer();
        server.start();
        HttpUrl requestUrl = server.url("/lol/league/v4/entries/by-summoner/");
        server.enqueue(new MockResponse().setResponseCode(403)
                .setBody("{\"status\": {\"message\":\"Data not found\",\"status_code\":403}"));

        Match match = new Match();
        ErrorMessage error = new ErrorMessage(403, "API fetch error: Forbidden");
        ResponseBody body = new ResponseBody(error);
        String PUUID = "B_BHDZwmQBwqszCtpjEHiTq1zZrcQZicLGyGhbA3M8jCk8WFRGGqoAA_uUc0vMzaVRBt7nZ_i_UMhA";
        String Region = "EUW";

        String[] reqHeaders = new String[]{"X-Riot-Token", "RGAPI-00000000-a3a8-4cee-86db-4dcba95e8f5d"};

        ResponseBody Matches = getQuery(URLBuilder.buildMatchRequestUrl(PUUID, Region), reqHeaders);
        System.out.println(URLBuilder.buildMatchRequestUrl(PUUID, Region));

        assertEquals(Matches.getMessage().getStatus(), body.getMessage().getStatus());
        assertEquals(Matches.getMessage().getDetail(), body.getMessage().getDetail());

        server.shutdown();
    }

    @Test
    // Test out of date queries
    public void getMatchsByPuuid_whenResponseBodyHasCorrectStructure_shouldReturnResponseBodyWithErrorCodeAndDetail() throws IOException {
        Match match = new Match();
        String PUUID = "zV2VdJjeV2rwsVndLJiA3s9FsTgo-3smdaLF0QfiN3EbYcfYmP4dbNRVwz9AE8JeIN2s3hoyDhyiDw";
        String Region = "oc1";
        Match m = new Match();
        ResponseBody Matches = getQuery(URLBuilder.buildMatchRequestUrl(PUUID, Region), Constants.requestHeaders);
        m.setMatchListByPUUID(Matches);
        List<String> actual = m.getMatchIds();
        String expectedRegex = "[A-Z0-9_]+";

        System.out.println(m.getMatchIds());
        for(int i = 0; i < actual.size(); i++) {
            assertEquals(true, actual.get(i).matches(expectedRegex));
        }
        
    }
}
//
//    @Test
//    public void getMatchsByPuuid_whenResponseIsValid_shouldReturnResponseBodyWithJSONStringAndErrorIsFalse() throws IOException {
//        server = new MockWebServer();
//        server.start();
//        String url = String.format("/lol/match/v5/matches/by-puuid/%s/ids",
//                RandomStringUtils.randomAlphanumeric(40));
//        HttpUrl requestUrl = server.url(url);
//
//        final String body = String.format("[\n" +
//                        "\"OC1_%s\",\n" +
//                        "\"OC1_%s\",\n" +
//                        "\"OC1_%s\"\n]",
//                RandomStringUtils.randomNumeric(9),
//                RandomStringUtils.randomNumeric(9),
//                RandomStringUtils.randomNumeric(9));
//        String[] reqHeaders = new String [] {"X-Riot-Token", "API-KEY"};
//
//        Match match = new Match();
//        ResponseBody testResponse = new ResponseBody(body, false);
//
//        server.enqueue(new MockResponse().setResponseCode(200).setBody(body));
//
//        ResponseBody responseBody = match.getMatchListByPUUID("summonerId", "region", String.valueOf(requestUrl), reqHeaders);
//        assertEquals(responseBody.getJson().getClass(), testResponse.getJson().getClass());
//        assertFalse(responseBody.isError());
//        server.shutdown();
//    }
//}
//
//
//
