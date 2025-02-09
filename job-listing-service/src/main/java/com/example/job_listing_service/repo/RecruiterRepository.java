package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Recruiters;
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
public interface RecruiterRepository extends JpaRepository<Recruiters, String> {

    Optional<Recruiters> findByUsername(String username);

    Optional<Recruiters> findByCompanyID(String companyID);

    @Modifying
    @Transactional
    @Query("UPDATE Recruiters r SET r.companyID = ?1 ,r.position = ?2 where r.username = ?3")
    void updateRecruiterDetails(String companyID, String position, String username);


    @Query("SELECT r FROM Recruiters r WHERE r.username LIKE %:username%")
    Page<Recruiters> searchRecruiters(@Param("username") String key, Pageable pageable);
}
