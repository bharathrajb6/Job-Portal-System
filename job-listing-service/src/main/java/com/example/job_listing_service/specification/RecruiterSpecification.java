package com.example.job_listing_service.specification;

import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Recruiters;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecruiterSpecification {

    public static Specification<Recruiters> getRecruiters(String username, Company company, String position) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (username != null) {
                predicateList.add(criteriaBuilder.equal(root.get("username"), username));
            }

            if (company != null) {
                predicateList.add(criteriaBuilder.equal(root.get("company"), company));
            }

            if (position != null) {
                predicateList.add(criteriaBuilder.equal(root.get("position"), position));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
