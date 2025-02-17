package com.example.job_listing_service.mapper;

import com.example.job_listing_service.dto.request.JobRequest;
import com.example.job_listing_service.model.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {

    Job toJob(JobRequest request);

}
