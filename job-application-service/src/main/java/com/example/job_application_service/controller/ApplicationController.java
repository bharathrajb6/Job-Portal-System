package com.example.job_application_service.controller;

import com.example.job_application_service.dto.request.JobApplicationRequest;
import com.example.job_application_service.dto.response.JobApplicationResponse;
import com.example.job_application_service.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final JobApplicationService jobApplicationService;

    /**
     * This method is used to apply for a job.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public JobApplicationResponse applyJob(@RequestBody JobApplicationRequest request) {
        return jobApplicationService.applyJob(request);
    }

    /**
     * This method is used to get the details of a job application.
     *
     * @param applicationID
     * @return
     */
    @RequestMapping(value = "/{applicationID}", method = RequestMethod.GET)
    public JobApplicationResponse getApplicationDetails(@PathVariable("applicationID") String applicationID) {
        return jobApplicationService.getApplicationDetails(applicationID);
    }

    /**
     * This method is used to withdraw a job application.
     *
     * @param applicationID
     * @return
     */
    @RequestMapping(value = "/{applicationID}/withdraw", method = RequestMethod.PUT)
    public JobApplicationResponse withdrawApplication(@PathVariable("applicationID") String applicationID) {
        return jobApplicationService.withdrawApplication(applicationID);
    }

    /**
     * This method is used to get all the applied jobs of a user.
     *
     * @param username
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/{username}/appliedJobs", method = RequestMethod.GET)
    public Page<JobApplicationResponse> getAllAppliedJobs(@PathVariable(value = "username") String username,
            Pageable pageable) {
        return jobApplicationService.getAllAppliedJobs(username, pageable);
    }

    /**
     * This method is used to update the status of a job application.
     *
     * @param applicationID
     * @param status
     * @return
     */
    @RequestMapping(value = "/{applicationID}/status", method = RequestMethod.PUT)
    public JobApplicationResponse updateJobApplicationStatus(@PathVariable("applicationID") String applicationID,
            @RequestParam("status") String status) {
        return jobApplicationService.updateApplicationStatus(applicationID, status);
    }

    /**
     * This method is used to get all the applications for a job used for recruiter.
     *
     * @param jobID
     * @param pageable
     * @return
     */
    @RequestMapping(value = "job/{id}", method = RequestMethod.GET)
    public Page<JobApplicationResponse> getAllApplicationsForJobID(String jobID, Pageable pageable) {
        return jobApplicationService.getAllApplicationsForJob(jobID, pageable);
    }
}
