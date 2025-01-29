package com.example.user_service.validation;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.exception.UserException;

import static com.example.user_service.messages.UserExceptionMessages.*;

public class UserValidation {

    /**
     * This method validates the user request
     *
     * @param userRequest
     */
    public static void validateUserRequest(UserRequest userRequest) {

        // Check if the user request is null
        if (userRequest == null) {
            throw new UserException(INVALID_USER_REQUEST);
        }

        String firstName = userRequest.getFirstName();
        String lastName = userRequest.getLastName();
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String email = userRequest.getEmail();
        String contactNumber = userRequest.getContactNumber();

        // Check if first name is null or empty
        if (firstName == null || firstName.isBlank() || firstName.isEmpty()) {
            throw new UserException(INVALID_FIRST_NAME);
        }

        // Check if last name is null or empty
        if (lastName == null || lastName.isBlank() || lastName.isEmpty()) {
            throw new UserException(INVALID_LAST_NAME);
        }

        // Check if username is null or empty
        if (username == null || username.isBlank() || username.isEmpty()) {
            throw new UserException(INVALID_USERNAME);
        }

        // Check if password is null or empty
        if (password == null || password.isBlank() || password.isEmpty()) {
            throw new UserException(INVALID_PASSWORD);
        }

        // Check if password length is less than 8
        if (password.length() < 8) {
            throw new UserException(INVALID_PASSWORD_LENGTH);
        }

        // Check if email is null or empty
        if (email == null || email.isBlank() || email.isEmpty()) {
            throw new UserException(INVALID_EMAIL);
        }

        // Check if contact number is null or empty
        if (contactNumber == null || contactNumber.isBlank() || contactNumber.isEmpty()) {
            throw new UserException(INVALID_CONTACT_NUMBER);
        }

        // Check if contact number length is not equal to 10
        if (contactNumber.length() != 10) {
            throw new UserException(INVALID_CONTACT_NUMBER_LENGTH);
        }
    }
}
