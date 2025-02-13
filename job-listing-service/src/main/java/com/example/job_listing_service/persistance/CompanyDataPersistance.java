package com.example.job_listing_service.persistance;

import com.example.job_listing_service.exception.CompanyException;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.repo.CompanyRepository;
import com.example.job_listing_service.specification.CompanySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyDataPersistance {

    private final CompanyRepository companyRepository;

    public void saveCompany(Company company) {
        try {
            companyRepository.save(company);
        } catch (Exception exception) {
            throw new CompanyException(exception.getMessage());
        }
    }

    public Company getCompanyDetails(String key) {
        if (key.startsWith("COM")) {
            return companyRepository.findByCompanyID(key).orElseThrow(() -> new CompanyException("Company not found"));
        }
        return companyRepository.findByCompanyName(key).orElseThrow(() -> new CompanyException("Company not found"));
    }

    public void updateCompanyDetails(Company company) {
        try {
            companyRepository.updateCompanyDetails(company.getCompanyName(), company.getWebsite(), company.getLocation(), company.getIndustry(), company.getCompanyID());
        } catch (Exception exception) {
            throw new CompanyException(exception.getMessage());
        }
    }

    public void deleteCompany(Company company) {
        try {
            companyRepository.delete(company);
        } catch (Exception exception) {
            throw new CompanyException(exception.getMessage());
        }
    }

    public Page<Company> getAllCompanies(String companyName, String location, String industry, Pageable pageable) {
        return companyRepository.findAll(CompanySpecification.getCompanies(companyName, location, industry), pageable);
    }

    public Page<Company> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public boolean isCompanyPresent(String companyName) {
        return companyRepository.findByCompanyName(companyName).isPresent();
    }
}
