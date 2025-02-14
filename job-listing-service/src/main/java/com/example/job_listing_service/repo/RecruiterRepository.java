package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Recruiters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface RecruiterRepository extends JpaRepository<Recruiters, String>, JpaSpecificationExecutor<Recruiters> {

    /**
     * Find recruiter by username
     *
     * @param username
     * @return
     */
    Optional<Recruiters> findByUsername(String username);

    /**
     * Update recruiter details
     *
     * @param companyID
     * @param position
     * @param username
     */
    @Modifying
    @Transactional
    @Query("UPDATE Recruiters r SET r.company = ?1 ,r.position = ?2 where r.username = ?3")
    void updateRecruiterDetails(Company companyID, String position, String username);

    /**
     * Find all recruiters with pagination
     *
     * @param pageable
     * @return
     */
    Page<Recruiters> findAll(Pageable pageable);

    /**
     * Find recruiters by company name
     *
     * @param company
     * @param pageable
     * @return
     */
    @Query("SELECT r from Recruiters r where r.company = ?1")
    Page<Recruiters> findByCompanyName(Company company, Pageable pageable);
}
