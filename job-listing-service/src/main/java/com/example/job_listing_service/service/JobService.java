package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    JobResponse postJob(JobRequest request);

    JobResponse getJobDetails(String jobID);

    JobResponse updateJob(String jobID, JobRequest request);

    void deleteJob(String jobID);

    JobResponse updateJobStatus(String jobID, JobState state);

    Page<JobResponse> getJobsByTitle(String title, Pageable pageable);

    Page<JobResponse> getJobsBySalary(double salary, Pageable pageable);

    Page<JobResponse> getJobsByLocation(String location, Pageable pageable);

    Page<JobResponse> searchJobs(String title, double salary, String location, JobType jobType, ExperienceLevel experienceLevel,String company);
}
