package com.example.job_listing_service.exception;

import com.example.job_listing_service.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle CompanyException
     *
     * @param companyException
     * @return
     */
    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<?> handleCompanyException(CompanyException companyException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Company Error", companyException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle RecruiterException
     *
     * @param recruiterException
     * @return
     */
    @ExceptionHandler(RecruiterException.class)
    public ResponseEntity<?> handleRecruiterException(RecruiterException recruiterException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Recruiter Error", recruiterException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle JobCategoryException
     *
     * @param jobCategoryException
     * @return
     */
    @ExceptionHandler(JobCategoryException.class)
    public ResponseEntity<?> handleJobCategoryException(JobCategoryException jobCategoryException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Job Category Error", jobCategoryException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle JobException
     *
     * @param jobException
     * @return
     */
    @ExceptionHandler(JobException.class)
    public ResponseEntity<?> handleJobException(JobException jobException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Job Error", jobException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
