package com.example.job_application_service.service;

import com.example.job_application_service.dto.request.JobApplicationRequest;
import com.example.job_application_service.dto.response.JobApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobApplicationService {

    JobApplicationResponse applyJob(JobApplicationRequest jobApplicationRequest);

    JobApplicationResponse getApplicationDetails(String applicationID);

    JobApplicationResponse withdrawApplication(String applicationID);

    Page<JobApplicationResponse> getAllAppliedJobs(String username, Pageable pageable);

    JobApplicationResponse updateApplicationStatus(String applicationID,String status);
}
