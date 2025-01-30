package com.example.user_service.validation;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.exception.UserException;
import com.example.user_service.model.Role;
import com.example.user_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.user_service.messages.UserExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    /**
     * This method validates the user request
     *
     * @param userRequest
     */
    public void validateUserRequest(UserRequest userRequest) {

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
        Role role = userRequest.getRole();
        String location = userRequest.getLocation();
        boolean isFresher = userRequest.isFresher();
        String companyName = userRequest.getCompanyName();
        String companyWebsite = userRequest.getCompanyWebsite();

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

        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserException(USERNAME_ALREADY_EXISTS);
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

        // Check if email is already registered
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException(EMAIL_ALREADY_EXISTS);
        }

        // Check if contact number is null or empty
        if (contactNumber == null || contactNumber.isBlank() || contactNumber.isEmpty()) {
            throw new UserException(INVALID_CONTACT_NUMBER);
        }

        // Check if contact number length is not equal to 10
        if (contactNumber.length() != 10) {
            throw new UserException(INVALID_CONTACT_NUMBER_LENGTH);
        }

        // Check if contact number is already registered
        if (userRepository.findByContactNumber(contactNumber).isPresent()) {
            throw new UserException(CONTACT_NUMBER_ALREADY_EXISTS);
        }

        // Check if location is null or empty
        if (location == null || location.isBlank() || location.isEmpty()) {
            throw new UserException(INVALID_LOCATION);
        }

        // Check if role is USER and isFresher is false
        if (role.equals(Role.USER) && !isFresher) {
            // Check if company name is null or empty
            if (companyName == null || companyName.isBlank() || companyName.isEmpty()) {
                throw new UserException(INVALID_COMPANY_NAME);
            }

            // Check if company website is null or empty
            if (companyWebsite == null || companyWebsite.isBlank() || companyWebsite.isEmpty()) {
                throw new UserException(INVALID_COMPANY_WEBSITE);
            }
        }
    }
}
