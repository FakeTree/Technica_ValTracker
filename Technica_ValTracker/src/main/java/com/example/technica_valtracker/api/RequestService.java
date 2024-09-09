package com.example.technica_valtracker.api;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import com.example.technica_valtracker.Constants;

import static com.example.technica_valtracker.utils.Deserialiser.*;

/**
 * Collection of functions that handle sending requests to the Riot API and parsing the output.
 */
public class RequestService {
    /**
     * Sends a GET request to the Riot API to the provided URL.
     * @param requestLink API request link
     * @return JSON response body as string
     */
    public static String sendGetRequest(String requestLink) throws IOException {
        String json;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.AL_RIOT_KEY)
                .url(requestLink)
                .build();

        // Send request to client, if successful parse as string
        try (Response response = client.newCall(request).execute()) {
            json = response.body().string();

            if (!response.isSuccessful()) {
                ErrorMessage error  = getErrorMessageFromJson(json);
                return error.getDetail();
            }

            if (json.isEmpty()) {
                ErrorMessage error = new ErrorMessage(404, "Error while fetching data from API");
                return error.getDetail();
            }
        }
        return json;
    }
}
