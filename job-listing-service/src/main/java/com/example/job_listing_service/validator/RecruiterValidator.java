package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.exception.RecruiterException;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.job_listing_service.messages.recruiter.RecruiterMessages.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class RecruiterValidator {

    private final RecruiterDataPersistance recruiterDataPersistance;

    /**
     * Validate recruiter details before saving to database
     *
     * @param request
     */
    public void validateRecruiterDetails(RecruiterRequest request) {

        // Check if request is null
        if (request == null) {
            log.warn(INVALID_RECRUITER_REQUEST_DATA);
            throw new RecruiterException(INVALID_RECRUITER_REQUEST_DATA);
        }

        String username = request.getUsername();
        String companyName = request.getCompanyName();
        String position = request.getPosition();

        // Check if username is null or empty
        if (username == null || username.trim().isEmpty()) {
            log.warn(INVALID_USERNAME);
            throw new RecruiterException(INVALID_USERNAME);
        }

        // Check if company name is null or empty
        if (companyName == null || companyName.trim().isEmpty()) {
            log.warn(INVALID_COMPANY);
            throw new RecruiterException(INVALID_COMPANY);
        }

        // Check if position is null or empty
        if (position == null || position.trim().isEmpty()) {
            log.warn(INVALID_POSITION);
            throw new RecruiterException(INVALID_POSITION);
        }

        // Check if username already exists
        boolean isUsernameAlreadyExists = recruiterDataPersistance.isRecruiterPresent(username);
        if (isUsernameAlreadyExists) {
            log.warn(USERNAME_ALREADY_EXISTS);
            throw new RecruiterException(USERNAME_ALREADY_EXISTS);
        }
    }

    /**
     * Validate recruiter details before updating to database
     *
     * @param username
     * @param companyID
     * @param position
     */
    public void validateUpdatedRecruiterCredentials(String username, String companyID, String position) {

        // Check if username is null or empty
        if (username == null || username.trim().isEmpty()) {
            log.warn(INVALID_USERNAME);
            throw new RecruiterException(INVALID_USERNAME);
        }

        // Check if company ID is null or empty
        if (companyID == null || companyID.trim().isEmpty()) {
            log.warn(INVALID_COMPANY);
            throw new RecruiterException(INVALID_COMPANY);
        }

        // Check if position is null or empty
        if (position == null || position.trim().isEmpty()) {
            log.warn(INVALID_POSITION);
            throw new RecruiterException(INVALID_POSITION);
        }
    }
}
