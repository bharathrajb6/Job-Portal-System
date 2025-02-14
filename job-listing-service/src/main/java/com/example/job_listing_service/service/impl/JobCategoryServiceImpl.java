package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.exception.JobCategoryException;
import com.example.job_listing_service.mapper.JobCategoryMapper;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.persistance.JobCategoryDataPersistance;
import com.example.job_listing_service.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.job_listing_service.utils.CommonUtils.generateRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryDataPersistance jobCategoryDataPersistance;
    private final JobCategoryMapper jobCategoryMapper;

    /**
     * Add a new job category to the database
     *
     * @param categoryRequest
     * @return
     */
    @Override
    public JobCategoryResponse addJobCategory(JobCategoryRequest categoryRequest) {
        String categoryName = categoryRequest.getCategoryName();
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new JobCategoryException("Category name cannot be null or empty");
        }
        JobCategory category = jobCategoryMapper.toJobCategory(categoryRequest);
        category.setCategoryID("CAT" + generateRandom());
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
        if (!categoryID.startsWith("CAT")) {
            throw new JobCategoryException("Invalid categoryID");
        }
        if (newCategoryName == null || newCategoryName.isEmpty()) {
            throw new JobCategoryException("Invalid category name");
        }
        boolean isJobCategoryPresent = jobCategoryDataPersistance.isCategoryPresent(categoryID);
        if (!isJobCategoryPresent) {
            throw new JobCategoryException("Job category not found");
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
