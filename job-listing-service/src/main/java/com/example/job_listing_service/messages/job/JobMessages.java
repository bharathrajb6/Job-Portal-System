package com.example.job_listing_service.messages.job;

public class JobMessages {
    public static final String JOB_ADDED_SUCCESSFULLY = "Job added successfully";
    public static final String JOB_OBJECT_CREATED = "Job object has been created successfully with ID - %s";
    public static final String JOB_UPDATED_SUCCESSFULLY = "Job updated successfully";
    public static final String JOB_STATUS_UPDATED_SUCCESSFULLY = "Job status has been updated successfully";
    public static final String JOB_DELETED_SUCCESSFULLY = "Job deleted successfully";
    public static final String JOB_NOT_FOUND_WITH_ID = "Job not found with ID - %s";
    public static final String UNABLE_TO_SAVE_JOB_DETAILS = "Unable to save job. Reason - %s";
    public static final String UNABLE_TO_UPDATE_JOB_DETAILS = "Unable to update job. Reason - %s";
    public static final String UNABLE_TO_DELETE_JOB_DETAILS = "Unable to delete job. Reason - %s";
    public static final String UNABLE_TO_UPDATE_JOB_STATUS = "Unable to update job status. Reason - %s";
    public static final String JOB_SEARCH_BASED_ON_CRITERIA = "Searching jobs based on the search criteria";
    public static final String ACTIVE_JOB_STATUS = "Already it is in this status";

    public static final String INVALID_JOB_REQUEST_DATA = "Request data cannot be null or empty";
    public static final String INVALID_JOB_TITLE = "Title cannot be null or empty";
    public static final String INVALID_JOB_TITLE_LENGTH = "Title must be between 3 and 100 characters long";
    public static final String INVALID_JOB_DESCRIPTION = "Description cannot be null or empty";
    public static final String INVALID_JOB_DESCRIPTION_LENGTH = "Description must be between 30 and 5000 characters long";
    public static final String INVALID_JOB_SALARY = "Salary must be between 0 and 10,00,000";
    public static final String INVALID_JOB_LOCATION = "Location cannot be null or empty";
    public static final String INVALID_JOB_LOCATION_FORMAT = "Invalid location format";
    public static final String INVALID_JOB_TYPE = "Job type cannot be empty";
    public static final String INVALID_JOB_EXPERIENCE = "Experience level cannot be empty";
    public static final String INVALID_JOB_COMPANY = "Company cannot be null or empty";
    public static final String INVALID_JOB_RECRUITER = "Recruiter cannot be null or empty";
    public static final String INVALID_JOB_CATEGORY = "Category cannot be null or empty";
    public static final String INVALID_JOB_STATUS = "Job state cannot be empty or closed.";
}
