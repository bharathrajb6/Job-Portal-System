package com.example.job_listing_service.mapper;

import com.example.job_listing_service.dto.request.JobCategoryRequest;
import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.model.JobCategory;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {

    JobCategory toJobCategory(JobCategoryRequest categoryRequest);

    JobCategoryResponse toJobCategoryResponse(JobCategory jobCategory);

    default Page<JobCategoryResponse> toJobCategoryPageResponse(Page<JobCategory> jobCategories) {
        List<JobCategoryResponse> jobCategoryResponseList = jobCategories.getContent().stream().map(this::toJobCategoryResponse).collect(Collectors.toList());
        return new PageImpl<>(jobCategoryResponseList, jobCategories.getPageable(), jobCategories.getTotalElements());
    }
}
