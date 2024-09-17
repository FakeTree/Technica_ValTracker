package com.example.technica_valtracker.api.error;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Intercepts HTTP responses and returns a response body with an ErrorMessage if an error status code was
 * sent back by the client. Ensures uniformity in the format of error responses.
 */
public class ErrorResponseInterceptor implements Interceptor {

    public static final MediaType APPLICATION_JSON = MediaType.get("application/json; charset=utf-8");

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Get response body
        Response response = chain.proceed(chain.request());

        // If response is not successful, replace with ErrorMessage with status code and reason
        if (!response.isSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = "API fetch error: " + ErrorMessage.getErrorReason(response.code());
            ErrorMessage error = new ErrorMessage(response.code(), message);

            String body = objectMapper.writeValueAsString(error);

            ResponseBody responseBody = ResponseBody.create(body, APPLICATION_JSON);

            ResponseBody originalBody = response.body();
            if (originalBody != null) {
                originalBody.close();
            }

            // Return new response body containing ErrorMessage
            return response.newBuilder().body(responseBody).build();
        }

        // Otherwise, return original response body
        return response;
    }
}