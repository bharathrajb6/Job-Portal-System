package com.example.job_listing_service.repo;

import com.example.job_listing_service.model.Recruiters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecruiterRepository extends JpaRepository<Recruiters, String> {


}
