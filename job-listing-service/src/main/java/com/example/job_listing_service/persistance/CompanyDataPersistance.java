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

import static com.example.job_listing_service.messages.company.CompanyMessages.*;
import static com.example.job_listing_service.utils.Constants.COMPANY_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyDataPersistance {

    private final CompanyRepository companyRepository;

    /**
     * Save company details to database
     *
     * @param company
     */
    public void saveCompany(Company company) {
        try {
            companyRepository.save(company);
            log.info(COMPANY_DETAILS_SAVED);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_SAVE_COMPANY_DETAILS, exception.getMessage()));
            throw new CompanyException(String.format(UNABLE_TO_SAVE_COMPANY_DETAILS, exception.getMessage()));
        }
    }

    /**
     * Get company details from database
     *
     * @param key
     * @return
     */
    public Company getCompanyDetails(String key) {
        if (key.startsWith(COMPANY_KEY)) {
            return companyRepository.findByCompanyID(key).orElseThrow(() -> {
                log.error(String.format(COMPANY_DETAILS_NOT_FOUND, key));
                return new CompanyException(String.format(COMPANY_DETAILS_NOT_FOUND, key));
            });
        }
        return companyRepository.findByCompanyName(key).orElseThrow(() -> {
            log.error(String.format(COMPANY_DETAILS_NOT_FOUND, key));
            return new CompanyException(String.format(COMPANY_DETAILS_NOT_FOUND, key));
        });
    }

    /**
     * Update company details in database
     *
     * @param company
     */
    public void updateCompanyDetails(Company company) {
        try {
            companyRepository.updateCompanyDetails(company.getCompanyName(), company.getWebsite(), company.getLocation(), company.getIndustry(), company.getCompanyID());
            log.info(COMPANY_DETAILS_UPDATED);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_UPDATE_COMPANY_DETAILS, exception.getMessage()));
            throw new CompanyException(String.format(UNABLE_TO_UPDATE_COMPANY_DETAILS, exception.getMessage()));
        }
    }

    /**
     * Delete company details from database
     *
     * @param company
     */
    public void deleteCompany(Company company) {
        try {
            companyRepository.delete(company);
            log.info(COMPANY_DETAILS_DELETED);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_DELETE_COMPANY_DETAILS, exception.getMessage()));
            throw new CompanyException(String.format(UNABLE_TO_DELETE_COMPANY_DETAILS, exception.getMessage()));
        }
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
    public Page<Company> searchCompanies(String companyName, String location, String industry, Pageable pageable) {
        return companyRepository.findAll(CompanySpecification.getCompanies(companyName, location, industry), pageable);
    }

    /**
     * Get all companies from database
     *
     * @param pageable
     * @return
     */
    public Page<Company> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    /**
     * Check if company is present in database
     *
     * @param companyName
     * @return
     */
    public boolean isCompanyPresent(String companyName) {
        return companyRepository.findByCompanyName(companyName).isPresent();
    }
}
