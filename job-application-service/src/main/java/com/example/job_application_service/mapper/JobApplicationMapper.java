package com.example.job_application_service.mapper;

import com.example.job_application_service.dto.request.JobApplicationRequest;
import com.example.job_application_service.dto.response.JobApplicationResponse;
import com.example.job_application_service.model.JobApplication;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    /**
     * This methods converts a JobApplicationRequest to a JobApplication.
     *
     * @param request
     * @return
     */
    JobApplication toJobApplication(JobApplicationRequest request);

    /**
     * This method converts a JobApplication to a JobApplicationResponse.
     *
     * @param jobApplication
     * @return
     */
    JobApplicationResponse toJobApplicationResponse(JobApplication jobApplication);

    /**
     * This method converts a Page of JobApplication to a Page of JobApplicationResponse.
     *
     * @param jobApplications
     * @return
     */
    default Page<JobApplicationResponse> toJobApplicationResponsePage(Page<JobApplication> jobApplications) {
        List<JobApplicationResponse> applicationResponseList =
                jobApplications.getContent().stream().map(this::toJobApplicationResponse).collect(Collectors.toList());
        return new PageImpl<>(applicationResponseList, jobApplications.getPageable(), jobApplications.getTotalPages());
    }
}
