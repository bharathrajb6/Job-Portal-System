package com.example.job_application_service.dto.response;

import com.example.job_application_service.dto.response.constants.ExperienceLevel;
import com.example.job_application_service.dto.response.constants.JobState;
import com.example.job_application_service.dto.response.constants.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse {
    private String jobID;
    private String title;
    private String description;
    private double salary;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String companyName;
    private String recruitersName;
    private String categoryName;
    private JobState jobState;
    private Timestamp postedAt;
}
