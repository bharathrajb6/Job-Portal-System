package com.example.job_listing_service.helper;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.mapper.JobMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.persistance.CompanyDataPersistance;
import com.example.job_listing_service.persistance.JobCategoryDataPersistance;
import com.example.job_listing_service.persistance.RecruiterDataPersistance;
import com.example.job_listing_service.validator.JobValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.job_listing_service.utils.CommonUtils.generateRandom;

@Component
@RequiredArgsConstructor
public class JobHelper {

    private final JobValidator jobValidator;
    private final JobMapper jobMapper;
    private final CompanyDataPersistance companyDataPersistance;
    private final RecruiterDataPersistance recruiterService;
    private final JobCategoryDataPersistance jobCategoryDataPersistance;

    /**
     * Generate job by job request
     *
     * @param jobRequest
     * @return
     */
    public Job generateJob(JobRequest jobRequest) {

        jobValidator.validateJobDetails(jobRequest);

        Company company = companyDataPersistance.getCompanyDetails(jobRequest.getCompanyName().toUpperCase());
        Recruiters recruiter = recruiterService.getRecruiterDetails(jobRequest.getRecruiterUsername().toUpperCase());
        JobCategory jobCategory = jobCategoryDataPersistance.getJobCategory(jobRequest.getCategoryName());

        Job job = jobMapper.toJob(jobRequest);
        job.setJobID("JOB" + generateRandom());
        job.setCompany(company);
        job.setRecruiters(recruiter);
        job.setCategory(jobCategory);
        return job;
    }
}
