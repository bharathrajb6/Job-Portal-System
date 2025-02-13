package com.example.job_listing_service.exception;

public class JobException extends RuntimeException {

    public JobException() {
    }

    public JobException(String message) {
        super(message);
    }
}
