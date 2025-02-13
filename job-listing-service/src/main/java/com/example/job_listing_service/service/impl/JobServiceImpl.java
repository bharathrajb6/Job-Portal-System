package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.helper.JobHelper;
import com.example.job_listing_service.mapper.JobMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.persistance.JobCategoryDataPersistance;
import com.example.job_listing_service.persistance.JobDataPersistance;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
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

    private final JobDataPersistance jobDataPersistance;
    private final CompanyDataPersistance companyDataPersistance;
    private final RecruiterDataPersistance recruiterDataPersistance;
    private final JobCategoryDataPersistance jobCategoryDataPersistance;
    private final JobMapper jobMapper;
    private final JobHelper jobHelper;

    @Override
    public JobResponse postJob(JobRequest request) {
        Job job = jobHelper.generateJob(request);
        jobDataPersistance.postJob(job);
        return getJobDetails(job.getJobID());
    }

    @Override
    public JobResponse getJobDetails(String jobID) {
        Job job = jobDataPersistance.getJobDetailsById(jobID);
        return jobMapper.toJobResponse(job);
    }

    @Override
    public JobResponse updateJob(String jobID, JobRequest request) {
        boolean isJobPresent = jobDataPersistance.isJobPresent(jobID);
        if (!isJobPresent) {
            throw new JobException("Job not found with ID");
        }

        Company company = companyDataPersistance.getCompanyDetails(request.getCompanyName());
        Recruiters recruiters = recruiterDataPersistance.getRecruiterDetails(request.getRecruiterUsername());
        JobCategory jobCategory = jobCategoryDataPersistance.getJobCategory(request.getCategoryName());

        Job job = jobDataPersistance.getJobDetailsById(jobID);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setCompany(company);
        job.setRecruiters(recruiters);
        job.setCategory(jobCategory);
        job.setJobState(request.getJobState());
        jobDataPersistance.updateJobDetails(job);
        return getJobDetails(jobID);
    }

    @Override
    public void deleteJob(String jobID) {
        boolean isJobPresent = jobDataPersistance.isJobPresent(jobID);
        if (isJobPresent) {
            jobDataPersistance.deleteJobDetails(jobID);
        } else {
            throw new JobException("Job not found");
        }
    }

    @Override
    public JobResponse updateJobStatus(String jobID, JobState state) {
        Job job = jobDataPersistance.getJobDetailsById(jobID);
        if (job.getJobState() == state) {
            throw new JobException("Already it is in this state");
        }
        jobDataPersistance.updateJobStatus(state, jobID);
        return getJobDetails(jobID);
    }

    @Override
    public Page<JobResponse> searchJobs(String title, double salary, String location, JobType jobType, ExperienceLevel experienceLevel, String companyID, Pageable pageable) {
        Company company = companyDataPersistance.getCompanyDetails(companyID);
        Page<Job> jobs = jobDataPersistance.searchJobs(title, salary, location, jobType, experienceLevel, company, pageable);
        return jobMapper.toJobResponsePage(jobs);
    }
}
