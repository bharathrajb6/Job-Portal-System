package com.example.job_listing_service.controller;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import com.example.job_listing_service.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * Post a new job
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public JobResponse postJob(@RequestBody JobRequest request) {
        return jobService.postJob(request);
    }

    /**
     * Get job details by jobID
     *
     * @param jobID
     * @return
     */
    @RequestMapping(value = "/job/{jobID}", method = RequestMethod.GET)
    public JobResponse getJobDetails(@PathVariable(value = "jobID") String jobID) {
        return jobService.getJobDetails(jobID);
    }

    /**
     * Update job details by jobID
     *
     * @param jobID
     * @param jobRequest
     * @return
     */
    @RequestMapping(value = "/job/{jobID}", method = RequestMethod.PUT)
    public JobResponse updateJobDetails(@PathVariable(value = "jobID") String jobID, @RequestBody JobRequest jobRequest) {
        return jobService.updateJob(jobID, jobRequest);
    }

    /**
     * Delete job by jobID
     *
     * @param jobID
     * @return
     */
    @RequestMapping(value = "/job/{jobID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteJob(@PathVariable(value = "jobID") String jobID) {
        jobService.deleteJob(jobID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update job status by jobID like ACTIVE, INACTIVE
     *
     * @param jobID
     * @param jobState
     * @return
     */
    @RequestMapping(value = "/job/{jobID}/status", method = RequestMethod.PUT)
    public JobResponse updateJobStatus(@PathVariable(value = "jobID") String jobID, @RequestParam(name = "state") JobState jobState) {
        return jobService.updateJobStatus(jobID, jobState);
    }

    /**
     * Search jobs by title, salary, location, jobType, experienceLevel, company
     *
     * @param title
     * @param salary
     * @param location
     * @param jobType
     * @param experienceLevel
     * @param company
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/job/search", method = RequestMethod.GET)
    public Page<JobResponse> searchJobs(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "salary", required = false) double salary, @RequestParam(value = "location", required = false) String location, @RequestParam(value = "jobType", required = false) JobType jobType, @RequestParam(value = "experience", required = false) ExperienceLevel experienceLevel, @RequestParam(value = "company", required = false) String company, Pageable pageable) {
        return jobService.searchJobs(title, salary, location, jobType, experienceLevel, company, pageable);
    }
}
