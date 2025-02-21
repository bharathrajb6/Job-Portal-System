package com.example.job_listing_service.helper;

import com.example.job_listing_service.dto.response.JobCategoryResponse;
import com.example.job_listing_service.model.JobCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobCategoryHelper {

    private final JobHelper jobHelper;

    /**
     * Convert JobCategory to JobCategoryResponse
     *
     * @param jobCategory
     * @return
     */
    public JobCategoryResponse toJobCategoryResponse(JobCategory jobCategory) {
        JobCategoryResponse jobCategoryResponse = new JobCategoryResponse();
        jobCategoryResponse.setCategoryID(jobCategory.getCategoryID());
        jobCategoryResponse.setCategoryName(jobCategory.getCategoryName());
        jobCategoryResponse.setJobResponses(jobCategory.getJobs().stream().map(jobHelper::toJobResponse).collect(Collectors.toList()));
        return jobCategoryResponse;
    }

    /**
     * Convert Page<JobCategory> to Page<JobCategoryResponse>
     *
     * @param jobCategories
     * @return
     */
    public Page<JobCategoryResponse> toJobCategoryPageResponse(Page<JobCategory> jobCategories) {
        List<JobCategoryResponse> jobCategoryResponseList = jobCategories.getContent().stream().map(this::toJobCategoryResponse).collect(Collectors.toList());
        return new PageImpl<>(jobCategoryResponseList, jobCategories.getPageable(), jobCategories.getTotalPages());
    }
}
