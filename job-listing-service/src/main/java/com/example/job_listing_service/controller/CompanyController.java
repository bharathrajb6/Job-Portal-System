package com.example.job_listing_service.controller;

import com.example.job_listing_service.dto.request.CompanyRequest;
import com.example.job_listing_service.dto.response.CompanyResponse;
import com.example.job_listing_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * Add a new company
     *
     * @param companyRequest
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.addCompany(companyRequest);
    }

    /**
     * Get company details by company name
     *
     * @param companyName
     * @return
     */
    @RequestMapping(value = "/company/{companyName}", method = RequestMethod.GET)
    public CompanyResponse getCompanyDetails(@PathVariable("companyName") String companyName) {
        return companyService.getCompanyDetails(companyName);
    }

    /**
     * Get all companies
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public Page<CompanyResponse> getAllCompanies(Pageable pageable) {
        return companyService.getAllCompanies(pageable);
    }

    /**
     * Update company details
     *
     * @param companyName
     * @param companyRequest
     * @return
     */
    @RequestMapping(value = "/company/{companyName}", method = RequestMethod.PUT)
    public CompanyResponse updateCompanyDetails(@PathVariable("companyName") String companyName, @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompanyDetails(companyName, companyRequest);
    }

    /**
     * Delete company details
     *
     * @param companyName
     * @return
     */
    @RequestMapping(value = "/company/{companyName}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyInfo(@PathVariable("companyName") String companyName) {
        companyService.deleteCompany(companyName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Search company by company name, location and industry
     *
     * @param companyName
     * @param location
     * @param industry
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/company/filter", method = RequestMethod.GET)
    public Page<CompanyResponse> searchCompany(@RequestParam(value = "companyName", required = false) String companyName,
                                               @RequestParam(value = "location", required = false) String location,
                                               @RequestParam(value = "industry", required = false) String industry,
                                               Pageable pageable) {
        return companyService.searchCompany(companyName, location, industry, pageable);
    }

}
