package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, String> {

    Optional<JobCategory> findByCategoryID(String categoryID);

    Optional<JobCategory> findByCategoryName(String categoryName);

    @Modifying
    @Transactional
    @Query("UPDATE JobCategory j set j.categoryName = ?1 where j.categoryID = ?2")
    void updateJobCategory(String categoryName, String categoryID);
}
