package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Company;
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
public interface CompanyRepository extends JpaRepository<Company, String>, JpaSpecificationExecutor<Company> {

    /**
     * Find company by company name
     *
     * @param companyName
     * @return
     */
    Optional<Company> findByCompanyName(String companyName);

    /**
     * Find company by company ID
     *
     * @param companyID
     * @return
     */
    Optional<Company> findByCompanyID(String companyID);

    /**
     * Update company details by company ID
     *
     * @param companyName
     * @param website
     * @param location
     * @param industry
     * @param companyID
     */
    @Modifying
    @Transactional
    @Query("UPDATE Company c SET c.companyName = ?1, c.website = ?2, c.location = ?3, c.industry = ?4 where c.companyID = ?5")
    void updateCompanyDetails(String companyName, String website, String location, String industry, String companyID);

    /**
     * Find all companies with pagination
     *
     * @param pageable
     * @return
     */
    Page<Company> findAll(Pageable pageable);

}
