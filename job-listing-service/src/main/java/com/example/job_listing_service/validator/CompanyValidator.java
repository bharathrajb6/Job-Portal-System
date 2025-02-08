package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.exception.CompanyException;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator {

    public void validateCompanyDetails(CompanyRequest companyRequest) {

        String companyName = companyRequest.getCompanyName();
        String website = companyRequest.getWebsite();
        String location = companyRequest.getLocation();
        String industry = companyRequest.getIndustry();

        if (companyName == null || companyName.isBlank() || companyName.isEmpty()) {
            throw new CompanyException("Company name cannot be null or empty");
        }

        if (website == null || website.isBlank() || website.isEmpty()) {
            throw new CompanyException("Website cannot be null or empty");
        }

        if (location == null || location.isBlank() || location.isEmpty()) {
            throw new CompanyException("Location cannot be null or empty");
        }

        if (industry == null || industry.isBlank() || industry.isEmpty()) {
            throw new CompanyException("Industry cannot be null or empty");
        }
    }
}
