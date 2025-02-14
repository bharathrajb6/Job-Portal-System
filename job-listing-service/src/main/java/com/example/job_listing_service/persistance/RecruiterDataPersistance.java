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

    /**
     * Save recruiter details to the database
     *
     * @param recruiters
     */
    public void saveRecruiterDetails(Recruiters recruiters) {
        try {
            recruiterRepository.save(recruiters);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    /**
     * Get recruiter details based on username
     *
     * @param username
     * @return
     */
    public Recruiters getRecruiterDetails(String username) {
        return recruiterRepository.findByUsername(username).orElseThrow(() -> new RecruiterException("Recruiter not found"));
    }

    /**
     * Update recruiter details
     *
     * @param company
     * @param position
     * @param username
     */
    public void updateRecruiterDetails(Company company, String position, String username) {
        try {
            recruiterRepository.updateRecruiterDetails(company, position, username);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    /**
     * Delete recruiter details
     *
     * @param recruiters
     */
    public void deleteRecruiterDetails(Recruiters recruiters) {
        try {
            recruiterRepository.delete(recruiters);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    /**
     * Search recruiter based on company, position and username
     *
     * @param username
     * @param company
     * @param position
     * @param pageable
     * @return
     */
    public Page<Recruiters> searchRecruiters(String username, Company company, String position, Pageable pageable) {
        return recruiterRepository.findAll(RecruiterSpecification.getRecruiters(username, company, position), pageable);
    }

    /**
     * Check if recruiter is present in the database
     *
     * @param username
     * @return
     */
    public boolean isRecruiterPresent(String username) {
        return recruiterRepository.findByUsername(username).isPresent();
    }

    /**
     * Get recruiters based on company
     *
     * @param company
     * @param pageable
     * @return
     */
    public Page<Recruiters> getRecruitersBasedOnCompany(Company company, Pageable pageable) {
        return recruiterRepository.findByCompanyName(company, pageable);
    }
}
