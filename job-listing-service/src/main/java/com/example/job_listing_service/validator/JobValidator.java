package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.job_listing_service.messages.job.JobMessages.*;
import static com.example.job_listing_service.utils.Constants.LOCATION_REGEX;

@Component
@Slf4j
public class JobValidator {

    /**
     * Validates the job details in the job request
     *
     * @param request
     */
    public void validateJobDetails(JobRequest request) {

        // Check if the job request is null
        if (request == null) {
            throw new JobException(INVALID_JOB_REQUEST_DATA);
        }

        String title = request.getTitle();
        String description = request.getDescription();
        double salary = request.getSalary();
        String location = request.getLocation();
        JobType jobType = request.getJobType();
        ExperienceLevel experienceLevel = request.getExperienceLevel();
        String company = request.getCompanyName();
        String recruiter = request.getRecruiterUsername();
        String category = request.getCategoryName();
        JobState jobState = request.getJobState();

        // Check if title is null or empty
        if (title == null || title.trim().isEmpty()) {
            log.warn(INVALID_JOB_TITLE);
            throw new JobException(INVALID_JOB_TITLE);
        }

        // Check if title length is less than 3 or greater than 100
        if (title.length() < 3 || title.length() > 100) {
            log.warn(INVALID_JOB_TITLE_LENGTH);
            throw new JobException(INVALID_JOB_TITLE_LENGTH);
        }

        // Check if description is null or empty
        if (description == null || description.trim().isEmpty()) {
            log.warn(INVALID_JOB_DESCRIPTION);
            throw new JobException(INVALID_JOB_DESCRIPTION);
        }

        // Check if description length is less than 30 or greater than 5000
        if (description.length() < 30 || description.length() > 5000) {
            log.warn(INVALID_JOB_DESCRIPTION_LENGTH);
            throw new JobException(INVALID_JOB_DESCRIPTION_LENGTH);
        }

        // Check if salary is less than or equal to 0 or greater than 10,000,000
        if (salary <= 0 || salary > 10_000_000) {
            log.warn(INVALID_JOB_SALARY);
            throw new JobException(INVALID_JOB_SALARY);
        }

        // Check if location is null or empty
        if (location == null || location.trim().isEmpty()) {
            log.warn(INVALID_JOB_LOCATION);
            throw new JobException(INVALID_JOB_LOCATION);
        }

        // Check if location does not match the location regex
        if (!location.matches(LOCATION_REGEX)) {
            log.warn(INVALID_JOB_LOCATION_FORMAT);
            throw new JobException(INVALID_JOB_LOCATION_FORMAT);
        }

        // Check if job type is null
        if (jobType == null) {
            log.warn(INVALID_JOB_TYPE);
            throw new JobException(INVALID_JOB_TYPE);
        }

        // Check if experience level is null
        if (experienceLevel == null) {
            log.warn(INVALID_JOB_EXPERIENCE);
            throw new JobException(INVALID_JOB_EXPERIENCE);
        }

        // Check if company is null or empty
        if (company == null || company.trim().isEmpty()) {
            log.warn(INVALID_JOB_COMPANY);
            throw new JobException(INVALID_JOB_COMPANY);
        }

        // Check if recruiter is null or empty
        if (recruiter == null || recruiter.trim().isEmpty()) {
            log.warn(INVALID_JOB_RECRUITER);
            throw new JobException(INVALID_JOB_RECRUITER);
        }

        // Check if category is null or empty
        if (category == null || category.trim().isEmpty()) {
            log.warn(INVALID_JOB_CATEGORY);
            throw new JobException(INVALID_JOB_CATEGORY);
        }

        // Check if job state is null or closed
        if (jobState == null || jobState.equals(JobState.CLOSED)) {
            log.warn(INVALID_JOB_STATUS);
            throw new JobException(INVALID_JOB_STATUS);
        }
    }
}
