package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.helper.JobHelper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.persistance.JobDataPersistance;
import com.example.job_listing_service.persistance.JobCategoryDataPersistance;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
import com.example.job_listing_service.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.example.job_listing_service.messages.job.JobMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobDataPersistance jobDataPersistance;
    private final CompanyDataPersistance companyDataPersistance;
    private final RecruiterDataPersistance recruiterDataPersistance;
    private final JobCategoryDataPersistance jobCategoryDataPersistance;
    private final JobHelper jobHelper;

    /**
     * Post a job to the system
     *
     * @param request
     * @return
     */
    @Override
    public JobResponse postJob(JobRequest request) {
        Job job = jobHelper.generateJob(request);
        jobDataPersistance.postJob(job);
        return getJobDetails(job.getJobID());
    }

    /**
     * Get job details by jobID
     *
     * @param jobID
     * @return
     */
    @Override
    public JobResponse getJobDetails(String jobID) {
        Job job = jobDataPersistance.getJobDetailsById(jobID);
        return jobHelper.toJobResponse(job);
    }

    /**
     * Update job details
     *
     * @param jobID
     * @param request
     * @return
     */
    @Override
    public JobResponse updateJob(String jobID, JobRequest request) {
        boolean isJobPresent = jobDataPersistance.isJobPresent(jobID);
        if (!isJobPresent) {
            log.error(String.format(JOB_NOT_FOUND_WITH_ID, jobID));
            throw new JobException(String.format(JOB_NOT_FOUND_WITH_ID, jobID));
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

    /**
     * Delete job by jobID
     *
     * @param jobID
     */
    @Override
    public void deleteJob(String jobID) {
        boolean isJobPresent = jobDataPersistance.isJobPresent(jobID);
        if (isJobPresent) {
            jobDataPersistance.deleteJobDetails(jobID);
            log.info(JOB_DELETED_SUCCESSFULLY);
        } else {
            log.error(String.format(JOB_NOT_FOUND_WITH_ID, jobID));
            throw new JobException(String.format(JOB_NOT_FOUND_WITH_ID, jobID));
        }
    }

    /**
     * Update job status
     *
     * @param jobID
     * @param state
     * @return
     */
    @Override
    public JobResponse updateJobStatus(String jobID, JobState state) {
        Job job = jobDataPersistance.getJobDetailsById(jobID);
        if (job.getJobState() == state) {
            throw new JobException(ACTIVE_JOB_STATUS);
        }
        jobDataPersistance.updateJobStatus(state, jobID);
        return getJobDetails(jobID);
    }

    /**
     * Search jobs based on title, salary, location, jobType, experienceLevel, companyID
     *
     * @param title
     * @param salary
     * @param location
     * @param jobType
     * @param experienceLevel
     * @param companyID
     * @param pageable
     * @return
     */
    @Override
    public Page<JobResponse> searchJobs(String title, double salary, String location, JobType jobType, ExperienceLevel experienceLevel, String companyID, Pageable pageable) {
        Company company = null;
        if (companyID != null) {
            company = companyDataPersistance.getCompanyDetails(companyID);
        }
        Page<Job> jobs = jobDataPersistance.searchJobs(title, salary, location, jobType, experienceLevel, company, pageable);
        return jobHelper.toJobResponsePage(jobs);
    }

    /**
     * Search jobs based on startDate and endDate
     *
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    @Override
    public Page<JobResponse> searchJobs(String startDate, String endDate, Pageable pageable) {
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } catch (DateTimeParseException exception) {
            throw new JobException(exception.getMessage());
        }
        Page<Job> jobs = jobDataPersistance.searchJobs(pageable);
        for (Job job : jobs) {
            boolean isValidJob = jobHelper.isWithinTimeRange(start, end, job.getPostedAt().toLocalDateTime().toLocalDate());
            if (!isValidJob) {
                jobs.getContent().remove(job);
            }
        }
        return jobHelper.toJobResponsePage(jobs);
    }
}
