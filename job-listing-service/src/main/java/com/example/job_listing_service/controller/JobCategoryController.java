package com.example.job_listing_service.controller;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    /**
     * Add a new job category
     *
     * @param categoryRequest
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public JobCategoryResponse addJobCategory(@RequestBody JobCategoryRequest categoryRequest) {
        return jobCategoryService.addJobCategory(categoryRequest);
    }

    /**
     * Get a job category by categoryID
     *
     * @param categoryID
     * @return
     */
    @RequestMapping(value = "/category/{categoryID}", method = RequestMethod.GET)
    public JobCategoryResponse getJobCategory(@PathVariable("categoryID") String categoryID) {
        return jobCategoryService.getJobCategory(categoryID);
    }

    /**
     * Get all job categories
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public Page<JobCategoryResponse> getAllJobCategories(Pageable pageable) {
        return jobCategoryService.getAllJobCategories(pageable);
    }

    /**
     * Update a job category
     *
     * @param categoryID
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "/category/{categoryID}", method = RequestMethod.PUT)
    public JobCategoryResponse updateJobCategory(@PathVariable("categoryID") String categoryID,
            @RequestParam("categoryName") String categoryName) {
        return jobCategoryService.updateJobCategory(categoryID, categoryName);
    }

    /**
     * Delete a job category by categoryID
     *
     * @param categoryID
     * @return
     */
    @RequestMapping(value = "/category/{categoryID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteJobCategory(@PathVariable("categoryID") String categoryID) {
        jobCategoryService.deleteJobCategory(categoryID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Search job categories by key
     *
     * @param key
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/category/filter", method = RequestMethod.GET)
    public Page<JobCategoryResponse> searchCategory(@RequestParam("key") String key, Pageable pageable) {
        return jobCategoryService.searchCategory(key, pageable);
    }

}
