package com.example.job_listing_service.validator;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.exception.CompanyException;
import com.example.job_listing_service.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.job_listing_service.messages.company.CompanyMessages.*;
import static com.example.job_listing_service.utils.Constants.HTTPS_KEY;
import static com.example.job_listing_service.utils.Constants.LOCATION_REGEX;

@Component
@Slf4j
public class CompanyValidator {

    /**
     * Validate company details such as company name, website, location and industry
     *
     * @param companyRequest
     */
    public void validateCompanyDetails(CompanyRequest companyRequest) {

        // Check if company request is null
        if (companyRequest == null) {
            throw new CompanyException(INVALID_COMPANY_REQUEST_DATA);
        }

        String companyName = companyRequest.getCompanyName();
        String website = companyRequest.getWebsite();
        String location = companyRequest.getLocation();
        String industry = companyRequest.getIndustry();

        // Check if company name is null or empty
        if (companyName == null || companyName.trim().isEmpty()) {
            log.warn(INVALID_COMPANY_NAME);
            throw new CompanyException(INVALID_COMPANY_NAME);
        }

        // Check if company name length is less than 2 or greater than 20
        if (companyName.length() < 2 || companyName.length() > 20) {
            log.warn(INVALID_COMPANY_NAME_LENGTH);
            throw new CompanyException(INVALID_COMPANY_NAME_LENGTH);
        }

        // Check if website is null or empty
        if (website == null || website.trim().isEmpty()) {
            log.warn(INVALID_COMPANY_WEBSITE);
            throw new CompanyException(INVALID_COMPANY_WEBSITE);
        }

        // Check if website length is less than 5 or greater than 30
        if (website.length() < 5 || website.length() > 30) {
            log.warn(INVALID_COMPANY_WEBSITE_LENGTH);
            throw new CompanyException(INVALID_COMPANY_WEBSITE_LENGTH);
        }

        // Check if website starts with https
        if (!website.startsWith(HTTPS_KEY)) {
            log.warn(INVALID_WEBSITE_URL_FORMAT);
            throw new CompanyException(INVALID_WEBSITE_URL_FORMAT);
        }

        // Check if location is null or empty
        if (location == null || location.trim().isEmpty()) {
            log.warn(INVALID_COMPANY_LOCATION);
            throw new CompanyException(INVALID_COMPANY_LOCATION);
        }

        // Check if location matches the location regex
        if (!location.matches(LOCATION_REGEX)) {
            log.warn(INVALID_COMPANY_LOCATION_FORMAT);
            throw new JobException(INVALID_COMPANY_LOCATION_FORMAT);
        }

        // Check if industry is null or empty
        if (industry == null || industry.trim().isEmpty()) {
            log.warn(INVALID_COMPANY_INDUSTRY);
            throw new CompanyException(INVALID_COMPANY_INDUSTRY);
        }
    }
}
