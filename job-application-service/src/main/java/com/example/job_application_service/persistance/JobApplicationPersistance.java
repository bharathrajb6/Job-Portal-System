package com.example.job_application_service.persistance;

import com.example.job_application_service.exceptions.JobApplicationException;
import com.example.job_application_service.model.ApplicationStatus;
import com.example.job_application_service.model.JobApplication;
import com.example.job_application_service.repo.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobApplicationPersistance {

    private final JobApplicationRepository jobApplicationRepository;

    /**
     * Saves the job application details to the database.
     *
     * @param jobApplication
     */
    public void saveApplicationDetails(JobApplication jobApplication) {
        try {
            jobApplicationRepository.save(jobApplication);
        } catch (Exception exception) {
            throw new JobApplicationException(exception.getMessage());
        }
    }

    /**
     * Retrieves the job application details from the database based on the application ID.
     *
     * @param applicationID
     * @return
     */
    public JobApplication getApplicationDetails(String applicationID) {
        return jobApplicationRepository.findById(applicationID)
                .orElseThrow(() -> new JobApplicationException("Application not found"));
    }

    /**
     * Updates the job application status to withdrawn.
     *
     * @param applicationID
     */
    public void withdrawJobApplication(String applicationID) {
        try {
            jobApplicationRepository.updateJobApplicationID(ApplicationStatus.WITHDRAWN, applicationID);
        } catch (Exception exception) {
            throw new JobApplicationException(exception.getMessage());
        }
    }

    /**
     * Retrieves all job applications for a specific user.
     *
     * @param username
     * @param pageable
     * @return
     */
    public Page<JobApplication> getAllAppliedJobs(String username, Pageable pageable) {
        Page<JobApplication> jobApplications = jobApplicationRepository.getAllAppliedJobs(username, pageable);
        return jobApplications;
    }

    /**
     * Updates the job application status.
     *
     * @param applicationID
     * @param status
     */
    public void updateJobApplicationStatus(String applicationID, ApplicationStatus status) {
        try {
            jobApplicationRepository.updateJobApplicationID(status, applicationID);
        } catch (Exception exception) {
            throw new JobApplicationException(exception.getMessage());
        }
    }

    /**
     * Retrieves all job applications for a specific job.
     *
     * @param jobID
     * @param pageable
     * @return
     */
    public Page<JobApplication> getAllApplicationsForJob(String jobID, Pageable pageable) {
        try {
            return jobApplicationRepository.getAllApplicationBasedOnJobID(jobID, pageable);
        } catch (Exception exception) {
            throw new JobApplicationException(exception.getMessage());
        }
    }

    /**
     * Retrieves all job applications for a specific job by status.
     *
     * @param jobID
     * @param status
     * @return
     */
    public Page<JobApplication> getAllApplicationForJobByStatus(String jobID, ApplicationStatus status) {
        try {
            return jobApplicationRepository.getApplicationsForJobByStatus(jobID, status);
        } catch (Exception exception) {
            throw new JobApplicationException(exception.getMessage());
        }
    }
}
