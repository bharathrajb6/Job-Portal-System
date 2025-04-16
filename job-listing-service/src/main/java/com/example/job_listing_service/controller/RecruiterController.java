package com.example.job_listing_service.controller;

import com.example.job_listing_service.dto.request.RecruiterRequest;
import com.example.job_listing_service.dto.response.RecruiterResponse;
import com.example.job_listing_service.service.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService recruiterService;

    /**
     * Add a new recruiter
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/recruiter", method = RequestMethod.POST)
    public RecruiterResponse addRecruiter(@RequestBody RecruiterRequest request) {
        return recruiterService.addRecruiter(request);
    }

    /**
     * Get recruiter details by username
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/recruiter/{username}", method = RequestMethod.GET)
    public RecruiterResponse getRecruiter(@PathVariable("username") String username) {
        return recruiterService.getRecruiter(username);
    }

    /**
     * Update recruiter details
     *
     * @param username
     * @param request
     * @return
     */
    @RequestMapping(value = "/recruiter/{username}", method = RequestMethod.PUT)
    public RecruiterResponse updateRecruiterDetails(@PathVariable("username") String username,
            @RequestBody RecruiterRequest request) {
        return recruiterService.updateRecruiterDetails(username, request);
    }

    /**
     * Delete a recruiter
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/recruiter/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRecruiter(@PathVariable("username") String username) {
        recruiterService.deleteRecruiter(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Search recruiter by username, company name and position
     *
     * @param username
     * @param companyName
     * @param position
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/recruiter/filter", method = RequestMethod.GET)
    public Page<RecruiterResponse> searchRecruiter(@RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "position", required = false) String position, Pageable pageable) {
        return recruiterService.searchRecruiter(username, companyName, position, pageable);
    }
}
