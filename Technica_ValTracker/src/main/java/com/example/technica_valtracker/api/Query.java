package com.example.technica_valtracker.api;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

/**
 * Handles all aspects of sending a GET request, including client and request setup, and checking for errors in the
 * response.
 */
public class Query {
    /**
     * Send a GET request to the Riot API to a specific endpoint and provide the
     * response as a ResponseBody.
     * @param url The request URL to query.
     * @param headers The authorisation headers to use.
     * @return A ResponseBody with the response as a JSON string if successful,
     * or with an ErrorMessage object if the request failed.
     */
    public static ResponseBody getQuery(String url, String[] headers) throws IOException  {
        String json;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header(headers[0], headers[1])
                .url(url)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // TODO: check for empty body e.g. "[]" output.

            // Parse successful response as string
            json = response.body().string();
        }

        return new ResponseBody(json, false);
    }
}
