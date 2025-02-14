package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.exception.CompanyException;
import com.example.job_listing_service.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CompanyValidator {

    /**
     * Validate company details such as company name, website, location and industry
     *
     * @param companyRequest
     */
    public void validateCompanyDetails(CompanyRequest companyRequest) {

        String companyName = companyRequest.getCompanyName();
        String website = companyRequest.getWebsite();
        String location = companyRequest.getLocation();
        String industry = companyRequest.getIndustry();

        if (companyName == null || companyName.trim().isEmpty()) {
            log.warn("Company name cannot be null or empty");
            throw new CompanyException("Company name cannot be null or empty");
        }

        if (companyName.length() < 2 || companyName.length() > 20) {
            log.warn("Company name must be between 2 to 20 characters long");
            throw new CompanyException("Company name must be between 2 to 20 characters long");
        }

        if (website == null || website.trim().isEmpty()) {
            throw new CompanyException("Website cannot be null or empty");
        }

        if (website.length() < 5 || website.length() > 30) {
            throw new CompanyException("Company website must be between 2 to 20 characters long");
        }

        if (!website.startsWith("https://")) {
            throw new CompanyException("Invalid company website URL");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new CompanyException("Location cannot be null or empty");
        }

        if (!location.matches("^[A-Za-z\\s,]+$")) {
            throw new JobException("Invalid location format");
        }

        if (industry == null || industry.trim().isEmpty()) {
            throw new CompanyException("Industry cannot be null or empty");
        }
    }
}
