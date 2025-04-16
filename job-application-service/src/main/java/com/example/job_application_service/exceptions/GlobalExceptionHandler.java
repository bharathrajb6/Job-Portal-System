package com.example.job_application_service.exceptions;

import com.example.job_application_service.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles JobApplicationException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(JobApplicationException.class)
    public ResponseEntity<?> handleJobApplicationException(JobApplicationException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Job Application Error", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
