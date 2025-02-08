package com.example.job_listing_service.exception;

public class CompanyException extends RuntimeException {

    public CompanyException() {
    }

    public CompanyException(String message) {
        super(message);
    }
}
