package com.example.job_listing_service.helper;

import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.dto.response.JobResponse;
import com.example.job_listing_service.model.Company;
import com.example.job_listing_service.model.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyHelper {

    private final JobHelper jobHelper;

    public CompanyResponse toCompanyResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompanyID(company.getCompanyID());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setWebsite(company.getWebsite());
        companyResponse.setIndustry(company.getIndustry());
        companyResponse.setLocation(company.getLocation());
        companyResponse.setCreatedAt(company.getCreatedAt());

        List<JobResponse> jobResponseList =
                company.getJobs().stream().map(jobHelper::toJobResponse).collect(Collectors.toList());
        companyResponse.setJobs(jobResponseList);
        return companyResponse;
    }

    public Page<CompanyResponse> toCompanyResponsePage(Page<Company> companies) {
        List<CompanyResponse> companyResponses =
                companies.getContent().stream().map(this::toCompanyResponse).collect(Collectors.toList());
        return new PageImpl<>(companyResponses, companies.getPageable(), companies.getTotalPages());
    }
}
