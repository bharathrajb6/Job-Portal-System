package com.example.job_listing_service.helper;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.mapper.JobMapper;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import com.example.job_listing_service.model.JobCategory;
import com.example.job_listing_service.model.Recruiters;
import com.example.job_listing_service.persistance.*;
import com.example.job_listing_service.validator.JobValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.job_listing_service.messages.job.JobMessages.JOB_OBJECT_CREATED;
import static com.example.job_listing_service.utils.CommonUtils.generateRandom;
import static com.example.job_listing_service.utils.Constants.JOB_KEY;

@Component
@RequiredArgsConstructor
@Slf4j
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
        job.setJobID(JOB_KEY + generateRandom());
        job.setCompany(company);
        job.setRecruiters(recruiter);
        job.setCategory(jobCategory);
        log.info(String.format(JOB_OBJECT_CREATED, job.getJobID()));
        return job;
    }


    public JobResponse toJobResponse(Job job) {
        JobResponse jobResponse = new JobResponse();
        jobResponse.setJobID(job.getJobID());
        jobResponse.setTitle(job.getTitle());
        jobResponse.setDescription(job.getDescription());
        jobResponse.setSalary(job.getSalary());
        jobResponse.setLocation(job.getLocation());
        jobResponse.setJobType(job.getJobType());
        jobResponse.setExperienceLevel(job.getExperienceLevel());
        jobResponse.setCompanyName(job.getCompany().getCompanyName());
        jobResponse.setRecruitersName(job.getRecruiters().getUsername());
        jobResponse.setCategoryName(job.getCategory().getCategoryName());
        jobResponse.setJobState(job.getJobState());
        jobResponse.setPostedAt(job.getPostedAt());
        return jobResponse;
    }

    public Page<JobResponse> toJobResponsePage(Page<Job> jobs) {
        List<JobResponse> jobResponses = jobs.stream().map(this::toJobResponse).collect(Collectors.toList());
        return new PageImpl<>(jobResponses, jobs.getPageable(), jobs.getTotalPages());
    }
}
