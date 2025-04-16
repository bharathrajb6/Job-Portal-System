package com.example.job_application_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationResponse {
    private String applicationID;
    private String jobID;
    private String applicantID;
    private String status;
    private Timestamp appliedAt;
}
