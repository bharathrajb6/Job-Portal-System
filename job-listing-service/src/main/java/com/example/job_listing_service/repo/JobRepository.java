package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    Optional<Job> findByJobID(String jobID);

    @Query("SELECT j from Job j where j.title like %:title%")
    Page<Job> findByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT j from Job j where j.salary=:salary")
    Page<Job> findBySalary(@Param("salary") Double salary, Pageable pageable);

    @Query("SELECT j from Job j where j.location like %:location%")
    Page<Job> findByLocation(@Param("location") String location, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.jobState = ?1 where j.jobID = ?2")
    void updateJobDetails(String title, String description, double salary, String location, JobType type, ExperienceLevel experienceLevel, String companyID, String recruiterID, String categoryID, String jobID);

    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.jobState = ?1 where j.jobID = ?2")
    void updateJobState(JobState jobState, String jobID);
}
