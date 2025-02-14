package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.JobCategory;
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
public interface JobCategoryRepository extends JpaRepository<JobCategory, String> {

    /**
     * Find JobCategory by categoryID
     *
     * @param categoryID
     * @return
     */
    Optional<JobCategory> findByCategoryID(String categoryID);

    /**
     * Find JobCategory by categoryName
     *
     * @param categoryName
     * @return
     */
    Optional<JobCategory> findByCategoryName(String categoryName);

    /**
     * Find all JobCategory
     *
     * @param pageable
     * @return
     */
    Page<JobCategory> findAll(Pageable pageable);

    /**
     * Update JobCategory by categoryID
     *
     * @param categoryName
     * @param categoryID
     */
    @Modifying
    @Transactional
    @Query("UPDATE JobCategory j set j.categoryName = ?1 where j.categoryID = ?2")
    void updateJobCategory(String categoryName, String categoryID);

    /**
     * Search JobCategory by key
     *
     * @param key
     * @param pageable
     * @return
     */
    @Query("SELECT j from JobCategory j where j.categoryName like %:key%")
    Page<JobCategory> searchCategory(@Param("key") String key, Pageable pageable);
}
