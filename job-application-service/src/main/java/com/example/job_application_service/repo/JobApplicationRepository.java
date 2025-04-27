package com.example.job_application_service.repo;

import com.example.job_application_service.model.ApplicationStatus;
import com.example.job_application_service.model.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, String> {

    /**
     * Method to update the status of a job application
     *
     * @param applicationStatus
     * @param applicationID
     */
    @Modifying
    @Transactional
    @Query("UPDATE JobApplication j SET j.status = ?1 where j.applicationID = ?2")
    void updateJobApplicationID(ApplicationStatus applicationStatus, String applicationID);

    /**
     * Method to get all the job applications for a given user
     *
     * @param username
     * @param pageable
     * @return
     */
    @Query("SELECT j FROM JobApplication j WHERE j.applicantID = ?1")
    Page<JobApplication> getAllAppliedJobs(String username, Pageable pageable);

    /**
     * Method to get all the job applications for a given job
     *
     * @param jobID
     * @param pageable
     * @return
     */
    @Query("SELECT j from JobApplication j where j.jobID = ?1")
    Page<JobApplication> getAllApplicationBasedOnJobID(String jobID, Pageable pageable);

    /**
     * Method to check if a user has already applied for a job
     *
     * @param username
     * @param jobID
     * @return
     */
    @Query("SELECT j from JobApplication j where j.applicantID = ?1 and j.jobID = ?2")
    Optional<JobApplication> isAlreadyApplied(String username, String jobID);

    /**
     * Method to get all the job applications for a given user and status
     *
     * @param jobID
     * @param status
     * @return
     */
    @Query("SELECT j from JobApplication j where j.jobID = ?1 and j.status = ?2")
    Page<JobApplication> getApplicationsForJobByStatus(String jobID, ApplicationStatus status);
}
