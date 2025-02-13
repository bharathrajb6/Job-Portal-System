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

    Optional<Recruiters> findByUsername(String username);

    Optional<Recruiters> findByCompany(Company company);

    @Modifying
    @Transactional
    @Query("UPDATE Recruiters r SET r.company = ?1 ,r.position = ?2 where r.username = ?3")
    void updateRecruiterDetails(Company companyID, String position, String username);

    Page<Recruiters> findAll(Pageable pageable);

    @Query("SELECT r from Recruiters r where r.company = ?1")
    Page<Recruiters> findByCompanyName(Company company, Pageable pageable);
}
