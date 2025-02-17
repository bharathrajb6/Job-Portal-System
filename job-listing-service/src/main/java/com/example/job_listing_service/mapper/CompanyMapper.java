package com.example.job_listing_service.mapper;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.model.Company;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toCompany(CompanyRequest companyRequest);

}
