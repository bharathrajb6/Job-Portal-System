package com.example.job_application_service.service;

import com.example.job_application_service.dto.response.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "job-listing-service")
public interface JobService {

    /**
     * This method is used to get the details of a job.
     *
     * @param jobID
     * @return
     */
    @RequestMapping(value = "/api/v1/job/{jobID}", method = RequestMethod.GET)
    JobResponse getJobDetails(@PathVariable(value = "jobID") String jobID);

}
