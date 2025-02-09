package com.example.job_listing_service.mapper;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import com.example.job_listing_service.model.Recruiters;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecruiterMapper {

    Recruiters toRecruiters(RecruiterRequest request);

    RecruiterResponse toRecruiterResponse(Recruiters recruiters);

    default Page<RecruiterResponse> toTransactionResponsePages(Page<Recruiters> recruiters) {
        List<RecruiterResponse> responses = recruiters.getContent()
                .stream()
                .map(this::toRecruiterResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, recruiters.getPageable(), recruiters.getTotalElements());
    }

}
