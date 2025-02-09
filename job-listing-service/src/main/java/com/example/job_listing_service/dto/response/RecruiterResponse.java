package com.example.job_listing_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruiterResponse {
    private String recruiterID;
    private String username;
    private String companyID;
    private String position;
    private Timestamp createdAt;
}
