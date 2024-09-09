package com.example.technica_valtracker.api.error;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Interceptor for all API request methods that returns a JSON object with
 * a status code and message, ensuring error responses are uniform across all requests.
 * Returns a Response object with an ErrorMessage if the request failed, or the original
 * request body if successful.
 */
public class ErrorResponseInterceptor implements Interceptor {

    public static final MediaType APPLICATION_JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorMessage error = new ErrorMessage(response.code(), "Error while fetching data from API");

            String body = objectMapper.writeValueAsString(error);

            ResponseBody responseBody = ResponseBody.create(body, APPLICATION_JSON);

            ResponseBody originalBody = response.body();
            if (originalBody != null) {
                originalBody.close();
            }

            return response.newBuilder().body(responseBody).build();
        }
        return response;
    }
}