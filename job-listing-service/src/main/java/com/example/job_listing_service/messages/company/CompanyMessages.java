package com.example.job_listing_service.messages.company;

public class CompanyMessages {

    public static final String COMPANY_DETAILS_SAVED = "Company details saved successfully";
    public static final String COMPANY_DETAILS_UPDATED = "Company details updated successfully";
    public static final String COMPANY_DETAILS_DELETED = "Company details deleted successfully";
    public static final String COMPANY_RETRIVED_SUCCESSFULLY = "Companies retrieved successfully";
    public static final String COMPANY_RETRIVED_SUCCESSFULLY_BASED_ON_SEARCH = "Companies retrieved successfully based on search criteria";
    public static final String UNABLE_TO_SAVE_COMPANY_DETAILS = "Unable to save company details. Reason - %s";
    public static final String UNABLE_TO_UPDATE_COMPANY_DETAILS = "Unable to update company details. Reason - %s";
    public static final String UNABLE_TO_DELETE_COMPANY_DETAILS = "Unable to delete company details. Reason - %s";
    public static final String COMPANY_DETAILS_NOT_FOUND = "Company details are not found with this %s";

    public static final String INVALID_COMPANY_NAME = "Company name cannot be null or empty";
    public static final String INVALID_COMPANY_NAME_LENGTH = "Company name must be between 2 to 20 characters long";
    public static final String INVALID_COMPANY_WEBSITE = "Website cannot be null or empty";
    public static final String INVALID_COMPANY_WEBSITE_LENGTH = "Company website must be between 2 to 20 characters long";
    public static final String INVALID_WEBSITE_URL_FORMAT = "Invalid company website URL";
    public static final String INVALID_COMPANY_LOCATION = "Location cannot be null or empty";
    public static final String INVALID_COMPANY_LOCATION_FORMAT = "Invalid location format";
    public static final String INVALID_COMPANY_INDUSTRY = "Industry cannot be null or empty";
    public static final String INVALID_COMPANY_REQUEST_DATA = "Request data cannot be null or empty";
}
