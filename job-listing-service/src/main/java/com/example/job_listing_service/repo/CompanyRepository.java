package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Company;
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
public interface CompanyRepository extends JpaRepository<Company, String> {

    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByCompanyID(String companyID);

    @Modifying
    @Transactional
    @Query("UPDATE Company c SET c.companyName = ?1, c.website = ?2, c.location = ?3, c.industry = ?4 where c.companyID = ?5")
    void updateCompanyDetails(String companyName, String website, String location, String industry, String companyID);

    @Query("SELECT c from Company c where c.companyName like %:key%")
    Page<Company> searchCompany(@Param("key") String key, Pageable pageable);
}
