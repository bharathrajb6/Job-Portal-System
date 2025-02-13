package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobValidator {

    public void validateJobDetails(JobRequest request) {
        String title = request.getTitle();
        String description = request.getDescription();
        double salary = request.getSalary();
        String location = request.getLocation();
        JobType jobType = request.getJobType();
        ExperienceLevel experienceLevel = request.getExperienceLevel();
        String company = request.getCompanyID();
        String recruiter = request.getRecruiterID();
        String category = request.getCategoryName();
        JobState jobState = request.getJobState();

        if (title == null || title.trim().isEmpty()) {
            log.warn("Title cannot be null or empty");
            throw new JobException("Title cannot be null or empty");
        }

        if (title.length() < 3 || title.length() > 100) {
            throw new JobException("Title must be between 3 and 100 characters long");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new JobException("Description cannot be null or empty");
        }

        if (description.length() < 30 || description.length() > 5000) {
            throw new JobException("Description must be between 30 and 5000 characters long");
        }

        if (salary <= 0 || salary > 10_000_000) {
            throw new JobException("Salary must be between 0 and 10,00,000");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new JobException("Location cannot be null or empty");
        }

        if (!location.matches("^[A-Za-z\\s,]+$")) {
            throw new JobException("Invalid location format");
        }

        if (jobType == null) {
            throw new JobException("Job type cannot be empty");
        }

        if (experienceLevel == null) {
            throw new JobException("Experience level cannot be empty");
        }

        if (company == null || company.trim().isEmpty()) {
            throw new JobException("Company cannot be null or empty");
        }

        if (recruiter == null || recruiter.trim().isEmpty()) {
            throw new JobException("Recruiter cannot be null or empty");
        }

        if (category == null || category.trim().isEmpty()) {
            throw new JobException("Category cannot be null or empty");
        }

        if (jobState == null || jobState.equals(JobState.CLOSED)) {
            throw new JobException("Job state cannot be empty or closed.");
        }
    }
}
