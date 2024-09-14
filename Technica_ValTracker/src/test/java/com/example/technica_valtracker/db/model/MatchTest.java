package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MatchTest {

    MockWebServer server;

    @Test
    public void getMatchsByPuuid_whenResponseBodyHasErrorStatusCode_shouldReturnResponseBodyWithErrorCodeAndDetail() throws IOException {
        server = new MockWebServer();
        server.start();
        HttpUrl requestUrl = server.url("/lol/league/v4/entries/by-summoner/");
        server.enqueue(new MockResponse().setResponseCode(404)
                .setBody("{\"status\": {\"message\":\"Data not found\",\"status_code\":404}"));

        Match match = new Match();
        ErrorMessage error = new ErrorMessage(404, "API fetch error: Data not found");
        ResponseBody body = new ResponseBody(error);

        String[] reqHeaders = new String[] {"X-Riot-Token", "API_KEY"};

        ResponseBody responseBody = match.getMatchListByPUUID("summonerId", "region", String.valueOf(requestUrl), reqHeaders);

        assertEquals(responseBody.getMessage().getStatus(), body.getMessage().getStatus());
        assertEquals(responseBody.getMessage().getDetail(), body.getMessage().getDetail());

        server.shutdown();
    }

    @Test
    public void getMatchsByPuuid_whenResponseIsValid_shouldReturnResponseBodyWithJSONStringAndErrorIsFalse() throws IOException {
        server = new MockWebServer();
        server.start();
        String url = String.format("/lol/match/v5/matches/by-puuid/%s/ids",
                RandomStringUtils.randomAlphanumeric(40));
        HttpUrl requestUrl = server.url(url);

        final String body = String.format("[\n" +
                        "\"OC1_%s\",\n" +
                        "\"OC1_%s\",\n" +
                        "\"OC1_%s\"\n]",
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomNumeric(9));
        String[] reqHeaders = new String [] {"X-Riot-Token", "API-KEY"};

        Match match = new Match();
        ResponseBody testResponse = new ResponseBody(body, false);

        server.enqueue(new MockResponse().setResponseCode(200).setBody(body));

        ResponseBody responseBody = match.getMatchListByPUUID("summonerId", "region", String.valueOf(requestUrl), reqHeaders);
        assertEquals(responseBody.getJson().getClass(), testResponse.getJson().getClass());
        assertFalse(responseBody.isError());
        server.shutdown();
    }
}



