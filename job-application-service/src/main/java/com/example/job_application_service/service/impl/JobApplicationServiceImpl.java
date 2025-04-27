package com.example.job_application_service.service.impl;

import com.example.job_application_service.dto.request.JobApplicationRequest;
import com.example.job_application_service.dto.response.JobApplicationResponse;
import com.example.job_application_service.dto.response.JobResponse;
import com.example.job_application_service.dto.response.constants.JobState;
import com.example.job_application_service.exceptions.JobApplicationException;
import com.example.job_application_service.helpers.ApplicationHelper;
import com.example.job_application_service.mapper.JobApplicationMapper;
import com.example.job_application_service.model.ApplicationStatus;
import com.example.job_application_service.model.JobApplication;
import com.example.job_application_service.persistance.JobApplicationPersistance;
import com.example.job_application_service.service.JobApplicationService;
import com.example.job_application_service.service.JobService;
import com.example.job_application_service.utils.Constants;
import com.example.job_application_service.validators.ApplicationValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.example.job_application_service.utils.CommonUtils.generateRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationPersistance jobApplicationPersistance;
    private final JobApplicationMapper jobApplicationMapper;
    private final JobService jobService;
    private final ApplicationValidator applicationValidator;
    private final ApplicationHelper applicationHelper;

    /**
     * This method is used to apply for a job
     *
     * @param jobApplicationRequest
     * @return
     */
    @Override
    public JobApplicationResponse applyJob(JobApplicationRequest jobApplicationRequest) {
        // Check if the job is open for applications
        JobResponse jobResponse = jobService.getJobDetails(jobApplicationRequest.getJobID());
        if (jobResponse.getJobState().equals(JobState.CLOSED)) {
            throw new JobApplicationException("No longer accepting applications");
        }
        // Check if the user has already applied for the job
        applicationValidator.isAlreadyApplied(jobApplicationRequest.getApplicantID(), jobApplicationRequest.getJobID());

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

    /**
     * This method is used to get all applications for a job
     *
     * @param jobID
     * @param pageable
     * @return
     */
    @Override
    public Page<JobApplicationResponse> getAllApplicationsForJob(String jobID, Pageable pageable) {
        Page<JobApplication> jobApplications = jobApplicationPersistance.getAllApplicationsForJob(jobID, pageable);
        return jobApplicationMapper.toJobApplicationResponsePage(jobApplications);
    }

    /**
     * This method is used to get all applications for a job by status.
     *
     * @param jobID
     * @param status
     * @return
     */
    @Override
    public Page<JobApplicationResponse> getAllApplicationForJobByStatus(String jobID, String status) {
        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status);
        Page<JobApplication> applications =
                jobApplicationPersistance.getAllApplicationForJobByStatus(jobID, applicationStatus);
        return jobApplicationMapper.toJobApplicationResponsePage(applications);
    }

    /**
     * This method is used to get all applications for a job by date.
     *
     * @param jobID
     * @param startDate
     * @param lastDate
     * @param pageable
     * @return
     */
    @Override
    public Page<JobApplicationResponse> getAllApplicationsForJobByDate(String jobID, String startDate, String lastDate,
            Pageable pageable) {
        LocalDate start, end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(lastDate);
        } catch (DateTimeParseException exception) {
            throw new JobApplicationException(exception.getMessage());
        }
        Page<JobApplication> jobApplications =
                jobApplicationPersistance.getAllApplicationForJobByStatus(jobID, ApplicationStatus.APPLIED);
        for (JobApplication jobApplication : jobApplications) {
            boolean isWithinRange = applicationHelper.isJobWithinRange(start, end,
                    jobApplication.getAppliedAt().toLocalDateTime().toLocalDate());
            if (!isWithinRange) {
                jobApplications.getContent().remove(jobApplication);
            }
        }
        return jobApplicationMapper.toJobApplicationResponsePage(jobApplications);
    }

}
