package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.exception.RecruiterException;
import com.example.job_listing_service.repo.RecruiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RecruiterValidator {

    private final RecruiterRepository recruiterRepository;

    public void validateRecruiterDetails(RecruiterRequest request) {
        String username = request.getUsername();
        String companyID = request.getCompanyID();
        String position = request.getPosition();

        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new RecruiterException("Username cannot be null or empty");
        }

        if (companyID == null || companyID.isEmpty() || companyID.isBlank()) {
            throw new RecruiterException("Company ID cannot be null or empty");
        }

        if (position == null || position.isEmpty() || position.isBlank()) {
            throw new RecruiterException("Position cannot be null or empty");
        }


        boolean isUsernameAlreadyExists = recruiterRepository.findByUsername(username).isPresent();
        if (isUsernameAlreadyExists) {
            throw new RecruiterException("Username already exists.");
        }
    }
}
