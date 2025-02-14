package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobCategoryService {

    JobCategoryResponse addJobCategory(JobCategoryRequest categoryRequest);

    JobCategoryResponse getJobCategory(String categoryName);

    Page<JobCategoryResponse> getAllJobCategories(Pageable pageable);

    JobCategoryResponse updateJobCategory(String categoryID, String newCategoryName);

    void deleteJobCategory(String categoryName);

    Page<JobCategoryResponse> searchCategory(String key, Pageable pageable);
}
