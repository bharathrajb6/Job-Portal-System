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

    @Override
    public JobCategoryResponse getJobCategory(String categoryName) {
        JobCategory jobCategory = jobCategoryDataPersistance.getJobCategory(categoryName);
        return jobCategoryMapper.toJobCategoryResponse(jobCategory);
    }

    @Override
    public JobCategoryResponse updateJobCategory(String categoryID, String newCategoryName) {
        if (!categoryID.startsWith("CATE")) {
            throw new JobCategoryException("Invalid categoryID");
        }
        if (newCategoryName == null || newCategoryName.isEmpty()) {
            throw new JobCategoryException("Invalid category name");
        }
        boolean isJobCategoryPresent = jobCategoryDataPersistance.isCategoryPresent(newCategoryName);
        if (!isJobCategoryPresent) {
            throw new JobCategoryException("Job category not found");
        }
        jobCategoryDataPersistance.updateJobCategory(newCategoryName, categoryID);
        return getJobCategory(categoryID);
    }

    @Override
    public void deleteJobCategory(String categoryName) {
        JobCategory jobCategory = jobCategoryDataPersistance.getJobCategory(categoryName);
        jobCategoryDataPersistance.deleteJobCategory(jobCategory);
    }

    @Override
    public Page<JobCategoryResponse> searchCategory(String key, Pageable pageable) {
        Page<JobCategory> jobCategories = jobCategoryDataPersistance.searchJobCategory(key, pageable);
        return jobCategoryMapper.toJobCategoryPageResponse(jobCategories);
    }
}
