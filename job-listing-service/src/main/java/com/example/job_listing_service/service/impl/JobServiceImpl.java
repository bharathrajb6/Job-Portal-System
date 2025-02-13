package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.helper.JobHelper;
import com.example.job_listing_service.mapper.JobMapper;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import com.example.job_listing_service.repo.JobRepository;
import com.example.job_listing_service.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final JobHelper jobHelper;

    @Override
    public JobResponse postJob(JobRequest request) {
        Job job = jobHelper.generateJob(request);
        try {
            jobRepository.save(job);
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
        return getJobDetails(job.getJobID());
    }

    @Override
    public JobResponse getJobDetails(String jobID) {
        Job job = jobRepository.findByJobID(jobID).orElseThrow(() -> new JobException("Job not found"));
        return jobMapper.toJobResponse(job);
    }

    @Override
    public JobResponse updateJob(String jobID, JobRequest request) {
        Job job = jobRepository.findByJobID(jobID).orElseThrow(() -> new JobException("Job not found with this ID"));
        try {
            jobRepository.updateJobDetails(request.getTitle(), request.getDescription(), request.getSalary(), request.getLocation(), request.getJobType(), request.getExperienceLevel(), request.getCompanyID(), request.getRecruiterID(), request.getCategoryName(), jobID);
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
        return getJobDetails(jobID);
    }

    @Override
    public void deleteJob(String jobID) {
        boolean isJobPresent = jobRepository.findByJobID(jobID).isPresent();
        if (isJobPresent) {
            try {
                jobRepository.deleteById(jobID);
            } catch (Exception exception) {
                throw new JobException(exception.getMessage());
            }
        }
    }

    @Override
    public JobResponse updateJobStatus(String jobID, JobState state) {
        Job job = jobRepository.findByJobID(jobID).orElseThrow(() -> new JobException("Job not found with this ID"));
        if (job.getJobState() == state) {
            throw new JobException("Already it is in this state");
        }
        try {
            jobRepository.updateJobState(state, jobID);
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
        return getJobDetails(jobID);
    }

    @Override
    public Page<JobResponse> getJobsByTitle(String title, Pageable pageable) {
        Page<Job> jobs = jobRepository.findByTitle(title, pageable);
        return jobMapper.toJobResponsePage(jobs);
    }

    @Override
    public Page<JobResponse> getJobsBySalary(double salary, Pageable pageable) {
        Page<Job> jobs = jobRepository.findBySalary(salary, pageable);
        return jobMapper.toJobResponsePage(jobs);
    }

    @Override
    public Page<JobResponse> getJobsByLocation(String location, Pageable pageable) {
        Page<Job> jobs = jobRepository.findByLocation(location, pageable);
        return jobMapper.toJobResponsePage(jobs);
    }

    @Override
    public Page<JobResponse> searchJobs(String title, double salary, String location, JobType jobType, ExperienceLevel experienceLevel, String company) {
        return null;
    }
}
