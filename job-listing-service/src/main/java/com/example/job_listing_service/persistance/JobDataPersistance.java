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

import static com.example.job_listing_service.messages.job.JobMessages.*;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobDataPersistance {

    private final JobRepository jobRepository;

    /**
     * Persists the job details in the database
     *
     * @param job
     */
    public void postJob(Job job) {
        try {
            jobRepository.save(job);
            log.info(JOB_ADDED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_SAVE_JOB_DETAILS, exception.getMessage()));
            throw new JobException(String.format(UNABLE_TO_SAVE_JOB_DETAILS, exception.getMessage()));
        }
    }

    /**
     * Fetches the job details by jobID
     *
     * @param jobID
     * @return
     */
    public Job getJobDetailsById(String jobID) {
        return jobRepository.findByJobID(jobID).orElseThrow(() -> {
            log.error(String.format(JOB_NOT_FOUND_WITH_ID, jobID));
            return new JobException(String.format(JOB_NOT_FOUND_WITH_ID, jobID));
        });
    }

    /**
     * Updates the job details in the database
     *
     * @param job
     */
    public void updateJobDetails(Job job) {
        try {
            jobRepository.updateJobDetails(job.getTitle(), job.getDescription(), job.getSalary(), job.getLocation(),
                    job.getJobType(), job.getExperienceLevel(), job.getCompany(), job.getRecruiters(),
                    job.getCategory(), job.getJobID());
            log.info(JOB_UPDATED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_UPDATE_JOB_DETAILS, exception.getMessage()));
            throw new JobException(String.format(UNABLE_TO_UPDATE_JOB_DETAILS, exception.getMessage()));
        }
    }

    /**
     * Deletes the job details from the database
     *
     * @param jobID
     */
    public void deleteJobDetails(String jobID) {
        try {
            jobRepository.deleteById(jobID);
            log.info(JOB_DELETED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_DELETE_JOB_DETAILS, exception.getMessage()));
            throw new JobException(String.format(UNABLE_TO_DELETE_JOB_DETAILS, exception.getMessage()));
        }
    }

    /**
     * Checks if the job is present in the database
     *
     * @param jobID
     * @return
     */
    public boolean isJobPresent(String jobID) {
        return jobRepository.findByJobID(jobID).isPresent();
    }

    /**
     * Updates the job status in the database
     *
     * @param jobState
     * @param jobID
     */
    public void updateJobStatus(JobState jobState, String jobID) {
        try {
            jobRepository.updateJobState(jobState, jobID);
            log.info(JOB_STATUS_UPDATED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_UPDATE_JOB_STATUS, exception.getMessage()));
            throw new JobException(String.format(UNABLE_TO_UPDATE_JOB_STATUS, exception.getMessage()));
        }
    }

    /**
     * Searches the jobs based on the search criteria
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
    public Page<Job> searchJobs(String title, double salary, String location, JobType jobType,
            ExperienceLevel experienceLevel, Company company, Pageable pageable) {
        log.info(JOB_SEARCH_BASED_ON_CRITERIA);
        return jobRepository.findAll(
                JobSpecification.getJobs(title, salary, location, jobType, experienceLevel, company), pageable);
    }

    /**
     * Searches the jobs based on the search criteria
     *
     * @param pageable
     * @return
     */
    public Page<Job> searchJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }
}
