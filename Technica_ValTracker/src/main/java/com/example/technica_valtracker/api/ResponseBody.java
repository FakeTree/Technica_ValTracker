package com.example.technica_valtracker.api;

import com.example.technica_valtracker.api.error.ErrorMessage;

/**
 * Holds a stringified JSON response body and value indicating
 * whether an error status code was received from the API.
 */
public class ResponseBody {
    private String json;
    private boolean error;
    private ErrorMessage message;

    public ResponseBody(String json, boolean error) {
        this.json = json;
        this.error = error;
    }

    public ResponseBody(ErrorMessage message) {
        this.message = message;
        this.error = true;
    }

    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }

    public boolean isError() {
        return error;
    }
    public void setError(boolean error) {
        this.error = error;
    }

    public ErrorMessage getMessage() { return message; }
    public void setMessage(ErrorMessage message) { this.message = message; }
}
