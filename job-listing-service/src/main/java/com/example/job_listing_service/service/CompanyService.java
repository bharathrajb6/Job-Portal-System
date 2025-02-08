package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;

public interface CompanyService {
    CompanyResponse addCompany(CompanyRequest companyRequest);

    CompanyResponse getCompanyDetails(String companyName);
}
