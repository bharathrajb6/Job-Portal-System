package com.example.job_listing_service.specification;

import com.example.job_listing_service.model.Company;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class CompanySpecification {

    /**
     * This method is used to get the companies based on the company name, location and industry
     *
     * @param companyName
     * @param location
     * @param industry
     * @return
     */
    public static Specification<Company> getCompanies(String companyName, String location, String industry) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (companyName != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyName"), companyName));
            }
            if (location != null) {
                predicates.add(criteriaBuilder.equal(root.get("location"), location));
            }
            if (industry != null) {
                predicates.add(criteriaBuilder.equal(root.get("industry"), industry));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
