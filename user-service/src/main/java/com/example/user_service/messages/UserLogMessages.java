package com.example.user_service.messages;

public class UserLogMessages {

    public static final String LOG_USER_REGISTRATION_SUCCESS = "User %s has been registered successfully";
    public static final String LOG_USER_REGISTRATION_FAILED = "Unable to register the user %s. Reason - %s";
    public static final String LOG_USER_DETAILS_UPDATE_SUCCESS = "User %s details has been updated successfully";
    public static final String LOG_USER_DETAIL_UPDATE_FAILED = "Unable to update the user %s details. Reason - %s";
    public static final String LOG_USER_DATA_NOT_FOUND = "User %s data not found";
    public static final String LOG_TOKEN_SAVED = "Token has been saved successfully";
    public static final String LOG_UNABLE_TO_SAVE_JWT_TOKEN_TO_DB = "Unable to save the token to database. Reason - %s";
    public static final String LOG_JWT_TOKEN_FOR_USER_DELETED_SUCCESS = "JWT Tokens for user %s has been deleted successfully";
    public static final String LOG_UNABLE_TO_DELETE_ALL_OLD_TOKENS = "Unable to delete all the old tokens for user %s. Reason - %s";
    public static final String LOG_USER_PASSWORD_UPDATE_SUCCESS = "User %s password is updated successfully";
    public static final String LOG_UNABLE_TO_UPDATE_PASSWORD = "Unable to update the user %s password. Reason - %s";

}
