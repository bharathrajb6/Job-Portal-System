package com.example.job_listing_service.exception;

public class JobCategoryException extends RuntimeException {
    public JobCategoryException() {
    }

    public JobCategoryException(String message) {
        super(message);
    }
}
