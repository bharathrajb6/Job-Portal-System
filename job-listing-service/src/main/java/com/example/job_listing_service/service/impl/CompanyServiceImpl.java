package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.mapper.CompanyMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
import com.example.job_listing_service.service.CompanyService;
import com.example.job_listing_service.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.job_listing_service.utils.CommonUtils.generateRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyValidator companyValidator;
    private final CompanyDataPersistance companyDataPersistance;
    private final CompanyMapper companyMapper;
    private final RecruiterDataPersistance recruiterDataPersistance;

    @Override
    public CompanyResponse addCompany(CompanyRequest companyRequest) {
        log.info("Validate the company details");
        companyValidator.validateCompanyDetails(companyRequest);
        Company company = companyMapper.toCompany(companyRequest);
        company.setCompanyID("COMP" + generateRandom());
        companyDataPersistance.saveCompany(company);
        return getCompanyDetails(company.getCompanyName());
    }

    @Override
    public CompanyResponse getCompanyDetails(String companyName) {
        Company company = companyDataPersistance.getCompanyDetails(companyName);
        return companyMapper.toCompanyResponse(company);
    }

    @Override
    public Page<CompanyResponse> getAllCompanies(Pageable pageable) {
        Page<Company> companies = companyDataPersistance.getAllCompanies(pageable);
        return companyMapper.toCompanyResponsePage(companies);
    }

    @Override
    public CompanyResponse updateCompanyDetails(String key, CompanyRequest companyRequest) {
        companyValidator.validateCompanyDetails(companyRequest);
        Company company = companyDataPersistance.getCompanyDetails(key);
        company.setCompanyName(companyRequest.getCompanyName());
        company.setWebsite(companyRequest.getWebsite());
        company.setLocation(companyRequest.getLocation());
        company.setIndustry(companyRequest.getIndustry());
        companyDataPersistance.updateCompanyDetails(company);
        return getCompanyDetails(company.getCompanyName());
    }

    @Override
    public void deleteCompany(String key) {
        Company company = companyDataPersistance.getCompanyDetails(key);
        companyDataPersistance.deleteCompany(company);
    }

    @Override
    public Page<CompanyResponse> searchCompany(String companyName, String location, String industry, Pageable pageable) {
        Page<Company> companies = companyDataPersistance.getAllCompanies(companyName, location, industry, pageable);
        return companyMapper.toCompanyResponsePage(companies);
    }
}
