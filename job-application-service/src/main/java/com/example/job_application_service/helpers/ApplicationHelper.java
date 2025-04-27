package com.example.job_application_service.helpers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ApplicationHelper {

    /**
     * This method checks if the job application date is within the specified range.
     *
     * @param startDate
     * @param endDate
     * @param appliedDate
     * @return
     */
    public boolean isJobWithinRange(LocalDate startDate, LocalDate endDate, LocalDate appliedDate) {
        return (startDate.equals(appliedDate) || endDate.equals(appliedDate) || (startDate.isBefore(
                appliedDate) && endDate.isAfter(appliedDate)));
    }
}
