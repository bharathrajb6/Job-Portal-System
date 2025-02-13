package com.example.job_listing_service.mapper;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.model.Job;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobMapper {

    Job toJob(JobRequest request);

    JobResponse toJobResponse(Job job);

    default Page<JobResponse> toJobResponsePage(Page<Job> jobs) {
        List<JobResponse> jobResponses = jobs.getContent().stream().map(this::toJobResponse).collect(Collectors.toList());
        return new PageImpl<>(jobResponses, jobs.getPageable(), jobs.getTotalPages());
    }
}
