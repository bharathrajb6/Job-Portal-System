package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import com.example.job_listing_service.exception.RecruiterException;
import com.example.job_listing_service.mapper.RecruiterMapper;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.repo.RecruiterRepository;
import com.example.job_listing_service.service.RecruiterService;
import com.example.job_listing_service.validator.RecruiterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.job_listing_service.utils.CommonUtils.generateRandom;


@Service
@Slf4j
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterValidator recruiterValidator;
    private final RecruiterMapper recruiterMapper;
    private final RecruiterRepository recruiterRepository;

    @Override
    public RecruiterResponse addRecruiter(RecruiterRequest request) {
        recruiterValidator.validateRecruiterDetails(request);
        Recruiters recruiters = recruiterMapper.toRecruiters(request);
        recruiters.setRecruiterID("REC" + generateRandom());
        try {
            recruiterRepository.save(recruiters);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
        return getRecruiter(request.getUsername());
    }

    @Override
    public RecruiterResponse getRecruiter(String username) {
        Recruiters recruiters = recruiterRepository.findByUsername(username).orElseThrow(() -> new RecruiterException("Recruiter not found"));
        return recruiterMapper.toRecruiterResponse(recruiters);
    }

    @Override
    public RecruiterResponse updateRecruiterDetails(String username, RecruiterRequest request) {
        String companyID = request.getCompanyID();
        String position = request.getPosition();
        validateUpdatedRecruiterCredentials(username, companyID, position);
        try {
            recruiterRepository.updateRecruiterDetails(companyID, position, username);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
        return getRecruiter(username);
    }

    @Override
    public void deleteRecruiter(String username) {
        Recruiters recruiters = recruiterRepository.findByUsername(username).orElseThrow(() -> new RecruiterException("Recruiter not found"));
        try {
            recruiterRepository.delete(recruiters);
        } catch (Exception exception) {
            throw new RecruiterException(exception.getMessage());
        }
    }

    @Override
    public Page<RecruiterResponse> searchRecruiter(String key, Pageable pageable) {
        Page<Recruiters> recruiters = recruiterRepository.searchRecruiters(key, pageable);
        return recruiterMapper.toTransactionResponsePages(recruiters);
    }

    private void validateUpdatedRecruiterCredentials(String username, String companyID, String position) {

        if (username == null || username.isEmpty()) {
            throw new RecruiterException("Username cannot be null or empty");
        }

        if (companyID == null || companyID.isEmpty()) {
            throw new RecruiterException("Company ID cannot be null or empty");
        }

        if (position == null || position.isEmpty()) {
            throw new RecruiterException("Position cannot be null or empty");
        }
    }
}
