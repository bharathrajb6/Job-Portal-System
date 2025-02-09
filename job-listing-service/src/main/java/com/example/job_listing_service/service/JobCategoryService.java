package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;

public interface JobCategoryService {

    JobCategoryResponse addJobCategory(JobCategoryRequest categoryRequest);

    JobCategoryResponse getJobCategory(String categoryName);

    JobCategoryResponse updateJobCategory(String categoryID,String newCategoryName);

    void deleteJobCategory(String categoryName);
}
