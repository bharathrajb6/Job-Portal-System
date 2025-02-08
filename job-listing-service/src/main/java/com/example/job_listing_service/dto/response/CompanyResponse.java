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
public class CompanyResponse{
    private String companyID;
    private String companyName;
    private String website;
    private String industry;
    private String location;
    private Timestamp createdAt;
}
