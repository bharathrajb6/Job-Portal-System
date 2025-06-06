package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyResponse addCompany(CompanyRequest companyRequest);

    CompanyResponse getCompanyDetails(String companyName);

    Page<CompanyResponse> getAllCompanies(Pageable pageable);

    CompanyResponse updateCompanyDetails(String companyName, CompanyRequest companyRequest);

    void deleteCompany(String companyName);

    Page<CompanyResponse> searchCompany(String companyName, String location, String industry, Pageable pageable);
}
