package com.example.user_service.messages;

public class UserExceptionMessages {
    public static final String INVALID_USER_REQUEST = "User request cannot be null";
    public static final String INVALID_FIRST_NAME = "First name cannot be null or empty";
    public static final String INVALID_LAST_NAME = "Last name cannot be null or empty";
    public static final String INVALID_USERNAME = "Username cannot be null or empty";
    public static final String USERNAME_ALREADY_EXISTS = "Username is already exists";
    public static final String INVALID_PASSWORD = "Password cannot be null or empty";
    public static final String INVALID_PASSWORD_LENGTH = "Password length is invalid. Password length should be minimum 8.";
    public static final String INVALID_EMAIL = "Email cannot be null or empty";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String INVALID_CONTACT_NUMBER = "Contact number cannot be null or empty";
    public static final String INVALID_CONTACT_NUMBER_LENGTH = "Contact number should have 10 digits";
    public static final String CONTACT_NUMBER_ALREADY_EXISTS = "Contact number already exists";
    public static final String INVALID_LOCATION = "Location cannot be null or empty";
    public static final String INVALID_COMPANY_NAME = "Company name cannot be null or empty for experienced professionals";
    public static final String INVALID_COMPANY_WEBSITE = "Company website cannot be null or empty for experienced professionals";
    public static final String USER_DATA_NOT_FOUND = "User %s data not found";
    public static final String UNABLE_TO_SAVE_TOKEN = "Unable to save the token to database. Reason - %s";
    public static final String UNABLE_TO_DELETE_ALL_OLD_TOKENS = "Unable to delete all the old token for user %s. Reason - %s";
    public static final String UNABLE_TO_UPDATE_USER_DETAILS = "Unable to update details for user %s. Reason - %s";
    public static final String UNABLE_TO_UPDATE_USER_PASSWORD = "Unable to update password for user %s. Reason - %s";
}
