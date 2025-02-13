package com.example.job_listing_service.persistance;


import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import com.example.job_listing_service.repo.JobRepository;
import com.example.job_listing_service.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class JobDataPersistance {

    private final JobRepository jobRepository;

    public void postJob(Job job) {
        try {
            jobRepository.save(job);
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
    }

    public Job getJobDetailsById(String jobID) {
        return jobRepository.findByJobID(jobID).orElseThrow(() -> new JobException("Job not found"));
    }

    public void updateJobDetails(Job job) {
        try {
            jobRepository.updateJobDetails(job.getTitle(), job.getDescription(), job.getSalary(), job.getLocation(), job.getJobType(), job.getExperienceLevel(), job.getCompany(), job.getRecruiters(), job.getCategory(), job.getJobID());
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
    }


    public void deleteJobDetails(String jobID) {
        try {
            jobRepository.deleteById(jobID);
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
    }

    public boolean isJobPresent(String jobID) {
        return jobRepository.findByJobID(jobID).isPresent();
    }

    public void updateJobStatus(JobState jobState, String jobID) {
        try {
            jobRepository.updateJobState(jobState, jobID);
        } catch (Exception exception) {
            throw new JobException(exception.getMessage());
        }
    }

    public Page<Job> searchJobs(String title, double salary, String location, JobType jobType, ExperienceLevel experienceLevel, Company company, Pageable pageable) {
        return jobRepository.findAll(JobSpecification.getJobs(title, salary, location, jobType, experienceLevel, company), pageable);
    }
}
