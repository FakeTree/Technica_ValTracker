import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    MockWebServer server;

    @BeforeEach
    public void initialiseMockServer() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    public void closeMockServer() throws IOException {
        server.shutdown();
    }

    @Test
    public void givenErrorResponseInterceptor_whenResponseBodyHasErrorStatusCode_shouldReturnErrorMessageObject() throws IOException {
        final String responseBody = "{\"status\":400,\"detail\":\"Error while fetching data from API\"}";

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
    }

    // Tests for generic GET request e.g. sendGetRequest //

    // Ensure that valid request returns a string
//    @Test
//    public void sendGetRequest_whenRequestIsSuccessful_shouldReturnResponseObject() {
//        final String responseBody = "{\"id\":\"123\",\"accountId\":\"456\",puuid:\"789\"," +
//            "\"profileIconId\":\"2389\",\"revisionDate\":\"14915734279\",\"summonerLevel\":\"4919\"}";
//
//        server.enqueue(new MockResponse().setResponseCode(200).setBody(responseBody));
//
//        HttpUrl baseUrl = server.url("/lol/summoner/v4/summoners/by-puuid/");
//    }



    // Throw errors appropriately

    // --- //

}
