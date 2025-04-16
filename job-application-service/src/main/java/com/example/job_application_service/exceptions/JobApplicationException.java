package com.example.job_application_service.exceptions;

public class JobApplicationException extends RuntimeException {

    public JobApplicationException(String message) {
        super(message);
    }

    public JobApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
