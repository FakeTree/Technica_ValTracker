package com.example.technica_valtracker.api.error;

/**
 * Holds HTTP error message and response.
 */

public class ErrorMessage {
    /* // TODO REMOVE LATER
    {
        "status": 404,
        "detail: "Data not found"
    }
     */
    private int status;
    private String detail;

    public ErrorMessage() {}

    public ErrorMessage(int status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public static String getErrorReason(int status) {
        return switch (status) {
            case 400 -> "Bad request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Data not found";
            case 405 -> "Method not allowed";
            case 415 -> "Unsupported media type";
            case 429 -> "Rate limit exceeded";
            case 500 -> "Internal server error";
            case 502 -> "Bad gateway";
            case 503 -> "Service unavailable";
            case 504 -> "Gateway timeout";
            default -> "Error fetching data from API";
        };
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

}
