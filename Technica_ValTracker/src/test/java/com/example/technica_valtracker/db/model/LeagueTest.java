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

public class LeagueTest {
    MockWebServer server;

    @Test
    public void getLeagueData_whenResponseBodyHasErrorStatusCode_shouldReturnResponseBodyWithErrorCodeAndDetail() throws IOException {
        server = new MockWebServer();
        server.start();
        HttpUrl requestUrl = server.url("/lol/league/v4/entries/by-summoner/");
        server.enqueue(new MockResponse().setResponseCode(404)
                .setBody("{\"status\": {\"message\":\"Data not found\",\"status_code\":404}"));

        League league = new League();
        ErrorMessage error = new ErrorMessage(404, "API fetch error: Data not found");
        ResponseBody body = new ResponseBody(error);

        String[] reqHeaders = new String[] {"X-Riot-Token", "API_KEY"};

        ResponseBody responseBody = league.getLeagueData("summonerId", "region", String.valueOf(requestUrl), reqHeaders);

        assertEquals(responseBody.getMessage().getStatus(), body.getMessage().getStatus());
        assertEquals(responseBody.getMessage().getDetail(), body.getMessage().getDetail());

        server.shutdown();
    }

    @Test
    public void getLeagueData_whenResponseIsValid_shouldReturnResponseBodyWithJSONStringAndErrorIsFalse() throws IOException {
        server = new MockWebServer();
        server.start();
        HttpUrl requestUrl = server.url("/lol/league/v4/entries/by-summoner/");

        final String body = String.format("[{\"leagueId\":\"%s\",\"summonerId\":\"%s\",\"leaguePoints\": \"%s\"}]",
                RandomStringUtils.randomAlphanumeric(40), RandomStringUtils.randomAlphanumeric(40),
                RandomStringUtils.randomNumeric(4));
        String[] reqHeaders = new String [] {"X-Riot-Token", "API-KEY"};

        League league = new League();
        ResponseBody testResponse = new ResponseBody(body, false);

        server.enqueue(new MockResponse().setResponseCode(200).setBody(body));

        ResponseBody responseBody = league.getLeagueData("summonerId", "region", String.valueOf(requestUrl), reqHeaders);
        assertEquals(responseBody.getJson().getClass(), testResponse.getJson().getClass());
        assertFalse(responseBody.isError());
        server.shutdown();
    }
}
