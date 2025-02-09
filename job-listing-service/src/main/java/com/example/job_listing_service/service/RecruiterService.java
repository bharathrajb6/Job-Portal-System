package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruiterService {

    RecruiterResponse addRecruiter(RecruiterRequest request);

    RecruiterResponse getRecruiter(String username);

    RecruiterResponse updateRecruiterDetails(String username, RecruiterRequest request);

    void deleteRecruiter(String username);

    Page<RecruiterResponse> searchRecruiter(String key, Pageable pageable);
}
