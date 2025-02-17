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

import static com.example.job_listing_service.messages.recruiter.RecruiterMessages.*;

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
            log.info(RECRUITER_SAVED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_SAVE_RECRUITER_DETAILS, exception.getMessage()));
            throw new RecruiterException(String.format(UNABLE_TO_SAVE_RECRUITER_DETAILS, exception.getMessage()));
        }
    }

    /**
     * Get recruiter details based on username
     *
     * @param username
     * @return
     */
    public Recruiters getRecruiterDetails(String username) {
        return recruiterRepository.findByUsername(username).orElseThrow(() -> {
            log.error(String.format(RECRUITER_NOT_FOUND, username));
            return new RecruiterException(String.format(RECRUITER_NOT_FOUND, username));
        });
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
            log.info(RECRUITER_UPDATED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_UPDATE_RECRUITER_DETAILS, exception.getMessage()));
            throw new RecruiterException(String.format(UNABLE_TO_UPDATE_RECRUITER_DETAILS, exception.getMessage()));
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
            log.info(RECRUITER_DELETED_SUCCESSFULLY);
        } catch (Exception exception) {
            log.error(String.format(UNABLE_TO_DELETE_RECRUITER_DETAILS, exception.getMessage()));
            throw new RecruiterException(String.format(UNABLE_TO_DELETE_RECRUITER_DETAILS, exception.getMessage()));
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
