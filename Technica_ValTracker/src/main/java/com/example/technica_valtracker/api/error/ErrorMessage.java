package com.example.technica_valtracker.api.error;

/**
 * Holds HTTP error message and response.
 */
public class ErrorMessage {
    private int status;
    private String detail;

    public ErrorMessage() {}

    public ErrorMessage(int status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

}
