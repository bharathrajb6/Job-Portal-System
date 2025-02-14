package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job> {

    /**
     * Find job by jobID in the database
     *
     * @param jobID
     * @return
     */
    Optional<Job> findByJobID(String jobID);

    /**
     * Update job details in the database
     *
     * @param title
     * @param description
     * @param salary
     * @param location
     * @param type
     * @param experienceLevel
     * @param company
     * @param recruiter
     * @param jobCategory
     * @param jobID
     */
    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.title = ?1, j.description = ?2, j.salary = ?3, j.location = ?4, j.jobType = ?5, j.experienceLevel = ?6, j.company = ?7, j.recruiters = ?8, j.category = ?9 where j.jobID = ?10")
    void updateJobDetails(String title, String description, double salary, String location, JobType type, ExperienceLevel experienceLevel, Company company, Recruiters recruiter, JobCategory jobCategory, String jobID);

    /**
     * Update job state in the database
     *
     * @param jobState
     * @param jobID
     */
    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.jobState = ?1 where j.jobID = ?2")
    void updateJobState(JobState jobState, String jobID);

    /**
     * Find all jobs with pagination
     *
     * @param pageable
     * @return
     */
    Page<Job> findAll(Pageable pageable);
}
