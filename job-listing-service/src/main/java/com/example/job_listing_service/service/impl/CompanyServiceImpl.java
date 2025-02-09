package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.exception.CompanyException;
import com.example.job_listing_service.mapper.CompanyMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.repo.CompanyRepository;
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

    private final CompanyRepository companyRepository;
    private final CompanyValidator companyValidator;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponse addCompany(CompanyRequest companyRequest) {
        companyValidator.validateCompanyDetails(companyRequest);
        Company company = companyMapper.toCompany(companyRequest);
        company.setCompanyID("COMP" + generateRandom());
        try {
            companyRepository.save(company);
        } catch (Exception exception) {
            throw new CompanyException(exception.getMessage());
        }
        return getCompanyDetails(company.getCompanyName());
    }

    @Override
    public CompanyResponse getCompanyDetails(String companyName) {
        if (companyName.startsWith("COMP")) {
            Company company = companyRepository.findByCompanyID(companyName).orElseThrow(() -> new CompanyException("Company not found"));
            return companyMapper.toCompanyResponse(company);
        }
        Company company = companyRepository.findByCompanyName(companyName).orElseThrow(() -> new CompanyException("Company not found"));
        return companyMapper.toCompanyResponse(company);
    }

    @Override
    public CompanyResponse updateCompanyDetails(String companyName, CompanyRequest companyRequest) {
        companyValidator.validateCompanyDetails(companyRequest);
        Company company = companyRepository.findByCompanyName(companyName).orElseThrow(() -> new CompanyException("Company not found"));
        try {
            companyRepository.updateCompanyDetails(companyRequest.getCompanyName(), companyRequest.getWebsite(), companyRequest.getLocation(), companyRequest.getIndustry(), company.getCompanyID());
        } catch (Exception exception) {
            throw new CompanyException(exception.getMessage());
        }
        return getCompanyDetails(company.getCompanyName());
    }

    @Override
    public void deleteCompany(String companyName) {
        Company company = companyRepository.findByCompanyName(companyName).orElseThrow(() -> new CompanyException("Company not found"));
        try {
            companyRepository.deleteById(company.getCompanyID());
        } catch (Exception exception) {
            throw new CompanyException(exception.getMessage());
        }
    }

    @Override
    public Page<CompanyResponse> searchCompany(String key, Pageable pageable) {
        Page<Company> companies = companyRepository.searchCompany(key, pageable);
        return companyMapper.toCompanyResponsePage(companies);
    }

}
