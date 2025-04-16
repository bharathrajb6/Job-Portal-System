package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.helper.CompanyHelper;
import com.example.job_listing_service.mapper.CompanyMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.service.CompanyService;
import com.example.job_listing_service.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.job_listing_service.messages.company.CompanyMessages.COMPANY_RETRIVED_SUCCESSFULLY;
import static com.example.job_listing_service.messages.company.CompanyMessages.COMPANY_RETRIVED_SUCCESSFULLY_BASED_ON_SEARCH;
import static com.example.job_listing_service.utils.CommonUtils.generateRandom;
import static com.example.job_listing_service.utils.Constants.COMPANY_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyValidator companyValidator;
    private final CompanyDataPersistance companyDataPersistance;
    private final CompanyMapper companyMapper;
    private final CompanyHelper companyHelper;

    /**
     * Add company details to the database
     *
     * @param companyRequest
     * @return
     */
    @Override
    public CompanyResponse addCompany(CompanyRequest companyRequest) {
        companyValidator.validateCompanyDetails(companyRequest);

        Company company = companyMapper.toCompany(companyRequest);
        company.setCompanyID(COMPANY_KEY + generateRandom());

        companyDataPersistance.saveCompany(company);

        return getCompanyDetails(company.getCompanyName());
    }

    /**
     * Get company details from the database by company name
     *
     * @param companyName
     * @return
     */
    @Override
    public CompanyResponse getCompanyDetails(String companyName) {
        Company company = companyDataPersistance.getCompanyDetails(companyName);
        return companyHelper.toCompanyResponse(company);
    }

    /**
     * Get all companies from the database
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<CompanyResponse> getAllCompanies(Pageable pageable) {
        Page<Company> companies = companyDataPersistance.getAllCompanies(pageable);
        log.info(COMPANY_RETRIVED_SUCCESSFULLY);
        return companyHelper.toCompanyResponsePage(companies);
    }

    /**
     * Update company details in the database
     *
     * @param key
     * @param companyRequest
     * @return
     */
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

    /**
     * Delete company details from the database
     *
     * @param key
     */
    @Override
    public void deleteCompany(String key) {
        Company company = companyDataPersistance.getCompanyDetails(key);
        companyDataPersistance.deleteCompany(company);
    }

    /**
     * Search companies based on company name, location and industry
     *
     * @param companyName
     * @param location
     * @param industry
     * @param pageable
     * @return
     */
    @Override
    public Page<CompanyResponse> searchCompany(String companyName, String location, String industry,
            Pageable pageable) {
        Page<Company> companies = companyDataPersistance.searchCompanies(companyName, location, industry, pageable);
        log.info(COMPANY_RETRIVED_SUCCESSFULLY_BASED_ON_SEARCH);
        return companyHelper.toCompanyResponsePage(companies);
    }
}
