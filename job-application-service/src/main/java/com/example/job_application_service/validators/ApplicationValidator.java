package com.example.job_application_service.validators;

import com.example.job_application_service.exceptions.JobApplicationException;
import com.example.job_application_service.model.JobApplication;
import com.example.job_application_service.repo.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    private final JobApplicationRepository jobApplicationRepository;

    /**
     * Check if the user has already applied for the job and throw an exception if they have.
     *
     * @param username
     * @param jobID
     */
    public void isAlreadyApplied(String username, String jobID) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.isAlreadyApplied(username, jobID);
        if (jobApplication.isPresent()) {
            throw new JobApplicationException("You have already applied for this position");
        }
    }

}
