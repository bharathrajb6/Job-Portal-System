package com.example.job_application_service.service.impl;

import com.example.job_application_service.dto.request.JobApplicationRequest;
import com.example.job_application_service.dto.response.JobApplicationResponse;
import com.example.job_application_service.mapper.JobApplicationMapper;
import com.example.job_application_service.model.ApplicationStatus;
import com.example.job_application_service.model.JobApplication;
import com.example.job_application_service.persistance.JobApplicationPersistance;
import com.example.job_application_service.service.JobApplicationService;
import com.example.job_application_service.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.job_application_service.utils.CommonUtils.generateRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationPersistance jobApplicationPersistance;
    private final JobApplicationMapper jobApplicationMapper;

    /**
     * This method is used to apply for a job
     *
     * @param jobApplicationRequest
     * @return
     */
    @Override
    public JobApplicationResponse applyJob(JobApplicationRequest jobApplicationRequest) {
        JobApplication jobApplication = jobApplicationMapper.toJobApplication(jobApplicationRequest);
        jobApplication.setApplicationID(Constants.JOB_APPLN_CONST + generateRandom());
        jobApplication.setStatus(ApplicationStatus.APPLIED);

        jobApplicationPersistance.saveApplicationDetails(jobApplication);
        return getApplicationDetails(jobApplication.getApplicationID());
    }

    /**
     * This method is used to get the application details for a job
     *
     * @param applicationID
     * @return
     */
    @Override
    public JobApplicationResponse getApplicationDetails(String applicationID) {
        JobApplication jobApplication = jobApplicationPersistance.getApplicationDetails(applicationID);
        return jobApplicationMapper.toJobApplicationResponse(jobApplication);
    }

    /**
     * This method is used to withdraw the application for a job
     *
     * @param applicationID
     * @return
     */
    @Override
    public JobApplicationResponse withdrawApplication(String applicationID) {
        jobApplicationPersistance.withdrawJobApplication(applicationID);
        return getApplicationDetails(applicationID);
    }

    /**
     * This method is used to get all applied jobs for a user
     *
     * @param username
     * @param pageable
     * @return
     */
    @Override
    public Page<JobApplicationResponse> getAllAppliedJobs(String username, Pageable pageable) {
        Page<JobApplication> jobApplications = jobApplicationPersistance.getAllAppliedJobs(username, pageable);
        return jobApplicationMapper.toJobApplicationResponsePage(jobApplications);
    }

    /**
     * This method is used to update the application status
     *
     * @param applicationID
     * @param status
     * @return
     */
    @Override
    public JobApplicationResponse updateApplicationStatus(String applicationID, String status) {
        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status);
        jobApplicationPersistance.updateJobApplicationStatus(applicationID, applicationStatus);
        return getApplicationDetails(applicationID);
    }

}
