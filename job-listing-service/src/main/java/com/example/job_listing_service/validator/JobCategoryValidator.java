package com.example.job_listing_service.validator;

import com.example.job_listing_service.exception.JobCategoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.job_listing_service.messages.JobCategory.JobCategoryMessages.INVALID_JOB_CATEGORY_NAME;
import static com.example.job_listing_service.messages.JobCategory.JobCategoryMessages.INVALID_JOB_CATEGORY_NAME_LENGTH;

@Component
@Slf4j
public class JobCategoryValidator {

    /**
     * Validates the job category name
     *
     * @param jobCategoryName
     */
    public void validateJobCategoryName(String jobCategoryName) {
        // Check if the job category name is null or empty
        if (jobCategoryName == null || jobCategoryName.trim().isEmpty()) {
            log.warn(INVALID_JOB_CATEGORY_NAME);
            throw new JobCategoryException(INVALID_JOB_CATEGORY_NAME);
        }

        // Check if the job category name length is less than 2 or greater than 20
        if (jobCategoryName.length() < 2 || jobCategoryName.length() > 20) {
            log.warn(INVALID_JOB_CATEGORY_NAME_LENGTH);
            throw new JobCategoryException(INVALID_JOB_CATEGORY_NAME_LENGTH);
        }
    }
}
