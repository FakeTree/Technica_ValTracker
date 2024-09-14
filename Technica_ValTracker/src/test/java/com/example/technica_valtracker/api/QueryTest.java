package com.example.technica_valtracker.api;

import com.example.technica_valtracker.api.error.ErrorMessage;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.technica_valtracker.api.Query.getQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class QueryTest {
    MockWebServer server;
    Random rand = new Random();

    final List<String> endPoints = Arrays.asList("/lol/summoner/v4/summoners/by-puuid/", "/lol/champion-mastery/v4/champion-masteries/by-puuid/",
            "/lol/league/v4/entries/by-summoner/");

    @Test
    public void getQuery_whenResponseBodyHasErrorStatusCode_shouldReturnResponseBodyWithErrorCodeAndDetail() throws IOException {
        server = new MockWebServer();
        server.start();

        // Get random endpoint
        HttpUrl requestUrl = server.url(endPoints.get(
                rand.nextInt(endPoints.size()))
        );

        final String[] reqHeaders = new String[] {"X-Riot-Token", "API_KEY"};

        server.enqueue(new MockResponse().setResponseCode(404)
                .setBody("{\"status\": {\"message\":\"Data not found\",\"status_code\":404}"));

        final ErrorMessage error = new ErrorMessage(404, "API fetch error: Data not found");
        final ResponseBody body = new ResponseBody(error);

        ResponseBody query = getQuery(String.valueOf(requestUrl), reqHeaders);

        assertEquals(query.getMessage().getStatus(), body.getMessage().getStatus());
        assertEquals(query.getMessage().getDetail(), body.getMessage().getDetail());
    }

    @Test
    public void getQuery_whenResponseIsValid_shouldReturnResponseBodyWithJSOnStringAndErrorEqualToFalse() throws IOException {
        server = new MockWebServer();
        server.start();

        final ResponseBody body = new ResponseBody("{\"exampleBody\": \"exampleValue\"}", false);

        // Get random endpoint
        HttpUrl requestUrl = server.url(endPoints.get(
                rand.nextInt(endPoints.size()))
        );

        final String[] reqHeaders = new String[] {"X-Riot-Token", "API_KEY"};

        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"exampleBody\": \"exampleValue\"}"));

        ResponseBody query = getQuery(String.valueOf(requestUrl), reqHeaders);

        assertEquals(query.getJson().getClass(), body.getJson().getClass());
        assertFalse(query.isError());
    }
}
