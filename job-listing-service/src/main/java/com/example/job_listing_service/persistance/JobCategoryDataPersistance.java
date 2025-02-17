package com.example.job_listing_service.persistance;

import com.example.job_listing_service.exception.JobCategoryException;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.repo.JobCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.example.job_listing_service.messages.JobCategory.JobCategoryMessages.*;
import static com.example.job_listing_service.utils.Constants.JOB_CATEGORY_KEY;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobCategoryDataPersistance {

    private final JobCategoryRepository jobCategoryRepository;

    /**
     * Save job category to database
     *
     * @param jobCategory
     */
    public void saveJobCategory(JobCategory jobCategory) {
        try {
            jobCategoryRepository.save(jobCategory);
            log.info(JOB_CATEGORY_ADDED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_SAVE_JOB_CATEGORY, exception.getMessage()));
            throw new JobCategoryException(String.format(UNABLE_TO_SAVE_JOB_CATEGORY, exception.getMessage()));
        }
    }

    /**
     * Get job category by key from database
     *
     * @param key
     * @return
     */
    public JobCategory getJobCategory(String key) {
        if (key.startsWith(JOB_CATEGORY_KEY)) {
            return jobCategoryRepository.findByCategoryID(key).orElseThrow(() -> {
                log.error(JOB_CATEGORY_NOT_FOUND);
                return new JobCategoryException(JOB_CATEGORY_NOT_FOUND);
            });
        }
        return jobCategoryRepository.findByCategoryName(key).orElseThrow(() -> {
            log.error(JOB_CATEGORY_NOT_FOUND);
            return new JobCategoryException(JOB_CATEGORY_NOT_FOUND);
        });
    }

    /**
     * Get all job categories from database
     *
     * @param pageable
     * @return
     */
    public Page<JobCategory> getAllJobCategories(Pageable pageable) {
        return jobCategoryRepository.findAll(pageable);
    }

    /**
     * Update job category in database
     *
     * @param newCategoryName
     * @param categoryID
     */
    public void updateJobCategory(String newCategoryName, String categoryID) {
        try {
            jobCategoryRepository.updateJobCategory(newCategoryName, categoryID);
            log.info(JOB_CATEGORY_UPDATED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_UPDATE_JOB_CATEGORY, exception.getMessage()));
            throw new JobCategoryException(String.format(UNABLE_TO_UPDATE_JOB_CATEGORY, exception.getMessage()));
        }
    }

    /**
     * Delete job category from database
     *
     * @param jobCategory
     */
    public void deleteJobCategory(JobCategory jobCategory) {
        try {
            jobCategoryRepository.delete(jobCategory);
            log.info(JOB_CATEGORY_DELETED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_DELETE_JOB_CATEGORY, exception.getMessage()));
            throw new JobCategoryException(String.format(UNABLE_TO_DELETE_JOB_CATEGORY, exception.getMessage()));
        }
    }

    /**
     * Check if category is present in database
     *
     * @param categoryName
     * @return
     */
    public boolean isCategoryPresent(String categoryName) {
        return jobCategoryRepository.findByCategoryName(categoryName).isPresent();
    }

    /**
     * Search job category by key
     *
     * @param key
     * @param pageable
     * @return
     */
    public Page<JobCategory> searchJobCategory(String key, Pageable pageable) {
        return jobCategoryRepository.searchCategory(key, pageable);
    }
}
