package com.example.job_listing_service.persistance;

import com.example.job_listing_service.exception.RecruiterException;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.repo.RecruiterRepository;
import com.example.job_listing_service.specification.RecruiterSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecruiterDataPersistance {

    private final RecruiterRepository recruiterRepository;

    public void saveRecruiterDetails(Recruiters recruiters) {
        try {
            recruiterRepository.save(recruiters);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    public Recruiters getRecruiterDetails(String username) {
        return recruiterRepository.findByUsername(username).orElseThrow(() -> new RecruiterException("Recruiter not found"));
    }


    public void updateRecruiterDetails(Company company, String position, String username) {
        try {
            recruiterRepository.updateRecruiterDetails(company, position, username);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    public void deleteRecruiterDetails(Recruiters recruiters) {
        try {
            recruiterRepository.delete(recruiters);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    public Page<Recruiters> searchCompany(String username, Company company, String position, Pageable pageable) {
        return recruiterRepository.findAll(RecruiterSpecification.getRecruiters(username, company, position), pageable);
    }

    public boolean isRecruiterPresent(String username) {
        return recruiterRepository.findByUsername(username).isPresent();
    }

    public Page<Recruiters> getRecruitersBasedOnCompany(Company company, Pageable pageable) {
        return recruiterRepository.findByCompanyName(company, pageable);
    }
}
