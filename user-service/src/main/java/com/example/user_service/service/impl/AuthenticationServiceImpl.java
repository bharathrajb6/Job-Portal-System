package com.example.user_service.service.impl;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.exception.UserException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.user_service.messages.UserExceptionMessages.*;
import static com.example.user_service.messages.UserLogMessages.*;
import static com.example.user_service.validation.UserValidation.validateUserRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenServiceImpl tokenService;

    /**
     * This method is used to register the user
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public String register(UserRequest request) {
        validateUserRequest(request);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            userRepository.save(user);
            log.info(String.format(LOG_USER_REGISTRATION_SUCCESS, request.getUsername()));
        } catch (Exception exception) {
            log.error(String.format(LOG_USER_REGISTRATION_FAILED, request.getUsername(), exception.getMessage()));
            throw new UserException(exception.getMessage());
        }
        String jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(jwtToken, user);
        return jwtToken;
    }

    /**
     * This method is used to log in the user
     *
     * @param request
     * @return
     */
    @Override
    public String login(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        validateLoginCredentials(username, password);

        try {
            // Authenticate the user with username and password
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception exception) {
            // If login credentials are wrong, then throw the exception
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            throw new UserException(String.format(USER_DATA_NOT_FOUND, username));
        }

        // Fetch the user details
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            return new UserException(String.format(USER_DATA_NOT_FOUND, username));
        });

        // Generate a new token
        String jwtToken = jwtService.generateToken(user);
        // Delete all the existing token which is generated for this user
        tokenService.revokeAllTokensForUser(username);
        // Save the new token
        tokenService.saveUserToken(jwtToken, user);
        return jwtToken;
    }

    /**
     * This method is used to validate the login credentials
     *
     * @param username
     * @param password
     */
    private void validateLoginCredentials(String username, String password) {

        if (username == null || username.isBlank() || username.isEmpty()) {
            throw new UserException(INVALID_USERNAME);
        }

        if (password == null || password.isBlank() || password.isEmpty()) {
            throw new UserException(INVALID_PASSWORD);
        }
    }

}
