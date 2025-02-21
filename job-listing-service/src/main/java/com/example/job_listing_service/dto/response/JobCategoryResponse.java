package com.example.job_listing_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCategoryResponse {
    private String categoryID;
    private String categoryName;
    private List<JobResponse> jobResponses;
}
