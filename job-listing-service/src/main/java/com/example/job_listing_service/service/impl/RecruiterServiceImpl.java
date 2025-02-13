package com.example.job_listing_service.service.impl;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import com.example.job_listing_service.exception.RecruiterException;
import com.example.job_listing_service.mapper.RecruiterMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
import com.example.job_listing_service.service.RecruiterService;
import com.example.job_listing_service.service.UserService;
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
    private final RecruiterDataPersistance recruiterDataPersistance;
    private final CompanyDataPersistance companyDataPersistance;
    private final UserService userService;

    @Override
    public RecruiterResponse addRecruiter(RecruiterRequest request) {
        // Validate the recruiter request
        recruiterValidator.validateRecruiterDetails(request);

        Recruiters recruiters = recruiterMapper.toRecruiters(request);
        Company company = companyDataPersistance.getCompanyDetails(request.getCompanyName());

        recruiters.setRecruiterID("REC" + generateRandom());
        recruiters.setCompany(company);

        recruiterDataPersistance.saveRecruiterDetails(recruiters);
        return getRecruiter(request.getUsername());
    }

    @Override
    public RecruiterResponse getRecruiter(String username) {
        Recruiters recruiters = recruiterDataPersistance.getRecruiterDetails(username);
        return recruiterMapper.toRecruiterResponse(recruiters);
    }

    @Override
    public RecruiterResponse updateRecruiterDetails(String username, RecruiterRequest request) {
        if (request == null) {
            throw new RecruiterException("Recruiter data cannot be null or empty");
        }
        String companyName = request.getCompanyName();
        String position = request.getPosition();
        recruiterValidator.validateUpdatedRecruiterCredentials(username, companyName, position);

        boolean isRecruiterPresent = recruiterDataPersistance.isRecruiterPresent(username);
        if (!isRecruiterPresent) {
            throw new RecruiterException("Recruiter not found with this username");
        }

        Company company = companyDataPersistance.getCompanyDetails(companyName);
        recruiterDataPersistance.updateRecruiterDetails(company, position, username);
        return getRecruiter(username);
    }

    @Override
    public void deleteRecruiter(String username) {
        Recruiters recruiters = recruiterDataPersistance.getRecruiterDetails(username);
        recruiterDataPersistance.deleteRecruiterDetails(recruiters);
    }

    @Override
    public Page<RecruiterResponse> searchRecruiter(String username, String companyName, String position, Pageable pageable) {
        Company company = companyDataPersistance.getCompanyDetails(companyName);
        Page<Recruiters> recruiters = recruiterDataPersistance.searchCompany(username, company, position, pageable);
        return recruiterMapper.toTransactionResponsePages(recruiters);
    }
}
