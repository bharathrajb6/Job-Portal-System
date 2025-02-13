package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.exception.JobCategoryException;
import com.example.job_listing_service.mapper.JobCategoryMapper;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.repo.JobCategoryRepository;
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

    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper jobCategoryMapper;

    @Override
    public JobCategoryResponse addJobCategory(JobCategoryRequest categoryRequest) {
        String categoryName = categoryRequest.getCategoryName();
        if (categoryName == null || categoryName.isEmpty()) {
            throw new JobCategoryException("Category name cannot be null or empty");
        }
        JobCategory category = jobCategoryMapper.toJobCategory(categoryRequest);
        category.setCategoryID("CAT" + generateRandom());
        try {
            jobCategoryRepository.save(category);
        } catch (Exception exception) {
            throw new JobCategoryException(exception.getMessage());
        }
        return getJobCategory(category.getCategoryName());
    }

    @Override
    public JobCategoryResponse getJobCategory(String categoryName) {
        if (categoryName.startsWith("CAT")) {
            JobCategory jobCategory = jobCategoryRepository.findByCategoryID(categoryName).orElseThrow(() -> new JobCategoryException("Category not found"));
            return jobCategoryMapper.toJobCategoryResponse(jobCategory);
        }
        JobCategory jobCategory = jobCategoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new JobCategoryException("Category not found"));
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
        boolean isJobCategoryPresent = jobCategoryRepository.findByCategoryID(categoryID).isPresent();
        if (!isJobCategoryPresent) {
            throw new JobCategoryException("Job category not found");
        }
        try {
            jobCategoryRepository.updateJobCategory(newCategoryName, categoryID);
        } catch (Exception exception) {
            throw new JobCategoryException(exception.getMessage());
        }
        return getJobCategory(categoryID);
    }

    @Override
    public void deleteJobCategory(String categoryName) {
        boolean isCategoryPresent = false;
        if (categoryName.startsWith("CAT")) {
            isCategoryPresent = jobCategoryRepository.findByCategoryID(categoryName).isPresent();
        }
        isCategoryPresent = jobCategoryRepository.findByCategoryName(categoryName).isPresent();
        if (!isCategoryPresent) {
            throw new JobCategoryException("Category not found");
        }
        try {
            jobCategoryRepository.deleteById(categoryName);
        } catch (Exception exception) {
            throw new JobCategoryException(exception.getMessage());
        }
    }

    @Override
    public Page<JobCategoryResponse> searchCategory(String key, Pageable pageable) {
        Page<JobCategory> jobCategories = jobCategoryRepository.searchCategory(key, pageable);
        return jobCategoryMapper.toJobCategoryPageResponse(jobCategories);
    }
}
