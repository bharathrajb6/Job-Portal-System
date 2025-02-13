package com.example.job_listing_service.persistance;

import com.example.job_listing_service.exception.JobCategoryException;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.repo.JobCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobCategoryDataPersistance {

    private final JobCategoryRepository jobCategoryRepository;

    public void saveJobCategory(JobCategory jobCategory) {
        try {
            jobCategoryRepository.save(jobCategory);
        } catch (Exception exception) {
            throw new JobCategoryException(exception.getMessage());
        }
    }

    public JobCategory getJobCategory(String key) {
        if (key.startsWith("CAT")) {
            return jobCategoryRepository.findByCategoryID(key).orElseThrow(() -> new JobCategoryException("Category not found"));
        }
        return jobCategoryRepository.findByCategoryName(key).orElseThrow(() -> new JobCategoryException("Category not found"));
    }

    public void updateJobCategory(String newCategoryName, String categoryID) {
        try {
            jobCategoryRepository.updateJobCategory(newCategoryName, categoryID);
        } catch (Exception exception) {
            throw new JobCategoryException(exception.getMessage());
        }
    }

    public void deleteJobCategory(JobCategory jobCategory) {
        try {
            jobCategoryRepository.delete(jobCategory);
        } catch (Exception exception) {
            throw new JobCategoryException(exception.getMessage());
        }
    }

    public boolean isCategoryPresent(String categoryName) {
        return jobCategoryRepository.findByCategoryName(categoryName).isPresent();
    }

    public Page<JobCategory> searchJobCategory(String key, Pageable pageable) {
        return jobCategoryRepository.searchCategory(key, pageable);
    }
}
