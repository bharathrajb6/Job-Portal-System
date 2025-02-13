package com.example.job_listing_service.helper;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import com.example.job_listing_service.exception.JobException;
import com.example.job_listing_service.mapper.JobMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
import com.example.job_listing_service.service.CompanyService;
import com.example.job_listing_service.service.JobCategoryService;
import com.example.job_listing_service.service.RecruiterService;
import com.example.job_listing_service.validator.JobValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.job_listing_service.utils.CommonUtils.generateRandom;

@Component
@RequiredArgsConstructor
public class JobHelper {

    private final JobValidator jobValidator;
    private final JobMapper jobMapper;
    private final CompanyDataPersistance companyDataPersistance;
    private final RecruiterDataPersistance recruiterService;
    private final JobCategoryService jobCategoryService;

    public Job generateJob(JobRequest jobRequest) {
        jobValidator.validateJobDetails(jobRequest);

        Company company = companyDataPersistance.getCompanyDetails(jobRequest.getCompanyID().toUpperCase());
        Recruiters recruiter = recruiterService.getRecruiterDetails(jobRequest.getRecruiterID().toUpperCase());
        JobCategoryResponse jobCategoryResponse = jobCategoryService.getJobCategory(jobRequest.getCategoryName().toUpperCase());

        Job job = jobMapper.toJob(jobRequest);
        job.setJobID("JOB" + generateRandom());
        job.setCompany(company);
        job.setRecruiters(recruiter);
        job.setCategoryID(jobCategoryResponse.getCategoryID());
        return job;
    }
}
