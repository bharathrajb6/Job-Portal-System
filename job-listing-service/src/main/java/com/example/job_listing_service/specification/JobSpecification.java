package com.example.job_listing_service.specification;

import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class JobSpecification {

    public static Specification<Job> getJobs(String title, double salary, String location, JobType jobType, ExperienceLevel experienceLevel, Company company) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (title != null) {
                predicates.add(criteriaBuilder.equal(root.get("title"), title));
            }

            if (salary > 0 && salary < 1_000_000) {
                predicates.add(criteriaBuilder.equal(root.get("salary"), salary));
            }

            if (location != null) {
                predicates.add(criteriaBuilder.equal(root.get("location"), location));
            }

            if (jobType != null) {
                predicates.add(criteriaBuilder.equal(root.get("jobType"), jobType));
            }

            if (experienceLevel != null) {
                predicates.add(criteriaBuilder.equal(root.get("experienceLevel"), experienceLevel));
            }

            if (company != null) {
                predicates.add(criteriaBuilder.equal(root.get("company"), company));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
