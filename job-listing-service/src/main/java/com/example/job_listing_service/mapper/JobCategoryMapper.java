package com.example.job_listing_service.mapper;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.model.JobCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {

    JobCategory toJobCategory(JobCategoryRequest categoryRequest);
}
