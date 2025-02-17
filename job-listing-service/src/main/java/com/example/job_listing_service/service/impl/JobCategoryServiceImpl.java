package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.exception.JobCategoryException;
import com.example.job_listing_service.mapper.JobCategoryMapper;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.persistance.*;
import com.example.job_listing_service.service.JobCategoryService;
import com.example.job_listing_service.validator.JobCategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.job_listing_service.messages.JobCategory.JobCategoryMessages.*;
import static com.example.job_listing_service.utils.CommonUtils.generateRandom;
import static com.example.job_listing_service.utils.Constants.JOB_CATEGORY_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryDataPersistance jobCategoryDataPersistance;
    private final JobCategoryMapper jobCategoryMapper;
    private final JobCategoryValidator jobCategoryValidator;

    /**
     * Add a new job category to the database
     *
     * @param categoryRequest
     * @return
     */
    @Override
    public JobCategoryResponse addJobCategory(JobCategoryRequest categoryRequest) {
        jobCategoryValidator.validateJobCategoryName(categoryRequest.getCategoryName());

        JobCategory category = jobCategoryMapper.toJobCategory(categoryRequest);
        category.setCategoryID(JOB_CATEGORY_KEY + generateRandom());

        jobCategoryDataPersistance.saveJobCategory(category);
        return getJobCategory(category.getCategoryName());
    }

    /**
     * Get a job category by name from the database
     *
     * @param categoryName
     * @return
     */
    @Override
    public JobCategoryResponse getJobCategory(String categoryName) {
        JobCategory jobCategory = jobCategoryDataPersistance.getJobCategory(categoryName);
        return jobCategoryMapper.toJobCategoryResponse(jobCategory);
    }

    /**
     * Get all job categories from the database with pagination
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<JobCategoryResponse> getAllJobCategories(Pageable pageable) {
        Page<JobCategory> jobCategories = jobCategoryDataPersistance.getAllJobCategories(pageable);
        log.info(JOB_CATEGORY_RETRIVED_SUCCESSFULLY);
        return jobCategoryMapper.toJobCategoryPageResponse(jobCategories);
    }

    /**
     * Update a job category in the database by categoryID
     *
     * @param categoryID
     * @param newCategoryName
     * @return
     */
    @Override
    public JobCategoryResponse updateJobCategory(String categoryID, String newCategoryName) {
        if (!categoryID.startsWith(JOB_CATEGORY_KEY)) {
            throw new JobCategoryException(INVALID_JOB_CATEGORY_ID);
        }
        jobCategoryValidator.validateJobCategoryName(newCategoryName);
        boolean isJobCategoryPresent = jobCategoryDataPersistance.isCategoryPresent(categoryID);
        if (!isJobCategoryPresent) {
            throw new JobCategoryException(JOB_CATEGORY_NOT_FOUND);
        }
        jobCategoryDataPersistance.updateJobCategory(newCategoryName, categoryID);
        return getJobCategory(categoryID);
    }

    /**
     * Delete a job category from the database by category name
     *
     * @param categoryName
     */
    @Override
    public void deleteJobCategory(String categoryName) {
        JobCategory jobCategory = jobCategoryDataPersistance.getJobCategory(categoryName);
        jobCategoryDataPersistance.deleteJobCategory(jobCategory);
    }

    /**
     * Search for job categories by category name in the database
     *
     * @param key
     * @param pageable
     * @return
     */
    @Override
    public Page<JobCategoryResponse> searchCategory(String key, Pageable pageable) {
        Page<JobCategory> jobCategories = jobCategoryDataPersistance.searchJobCategory(key, pageable);
        return jobCategoryMapper.toJobCategoryPageResponse(jobCategories);
    }
}
