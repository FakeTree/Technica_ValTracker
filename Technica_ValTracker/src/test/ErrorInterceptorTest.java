import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorInterceptorTest {

    MockWebServer server;

    @Test
    public void givenErrorResponseInterceptor_whenResponseBodyHasErrorStatusCode_shouldReturnErrorMessageObject() throws IOException {
        server = new MockWebServer();
        server.start();

        final String responseBody = "{\"status\":400,\"detail\":\"API fetch error: Bad request\"}";

        server.enqueue(new MockResponse().setResponseCode(400)
                .setBody("{\"status\": {\"message\":\"Bad Request\",\"status_code\":400}"));

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        Request request = new Request.Builder()
                .url(server.url("/lol/summoner/v4/summoners/by-puuid/"))
                .header("X-Riot-Token", "API_KEY")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(responseBody, response.body().string());
        }

        server.shutdown();
    }

}
