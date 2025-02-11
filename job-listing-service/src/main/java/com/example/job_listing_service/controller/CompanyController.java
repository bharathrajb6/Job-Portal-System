package com.example.job_listing_service.controller;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.addCompany(companyRequest);
    }

    @RequestMapping(value = "/company/{companyName}", method = RequestMethod.GET)
    public CompanyResponse getCompanyDetails(@PathVariable("companyName") String companyName) {
        return companyService.getCompanyDetails(companyName);
    }

    @RequestMapping(value = "/company/{companyName}", method = RequestMethod.PUT)
    public CompanyResponse updateCompanyDetails(@PathVariable("companyName") String companyName, @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompanyDetails(companyName, companyRequest);
    }

    @RequestMapping(value = "/company/{companyName}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyInfo(@PathVariable("companyName") String companyName) {
        companyService.deleteCompany(companyName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/company/filter", method = RequestMethod.GET)
    public Page<CompanyResponse> searchCompany(@RequestParam("key") String key, Pageable pageable) {
        return companyService.searchCompany(key, pageable);
    }

}
