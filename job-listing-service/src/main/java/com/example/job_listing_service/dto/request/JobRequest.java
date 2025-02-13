package com.example.job_listing_service.dto.request;

import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequest {
    private String title;
    private String description;
    private double salary;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String companyName;
    private String recruiterUsername;
    private String categoryName;
    private JobState jobState;
}
