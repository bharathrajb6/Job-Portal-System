package com.example.user_service.service.impl;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.exception.UserException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.UserService;
import com.example.user_service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.user_service.messages.UserExceptionMessages.*;
import static com.example.user_service.messages.UserLogMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final RedisServiceImpl redisService;

    /**
     * This method is used to get the username of the current user
     *
     * @return
     */
    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * This method is used to get the user details
     *
     * @return
     */
    @Override
    public UserResponse getUserDetails() {
        String username = getUsername();
        UserResponse userResponse = redisService.getData(username, UserResponse.class);
        if (userResponse != null) {
            return userResponse;
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            return new UserException(String.format(USER_DATA_NOT_FOUND, username));
        });
        userResponse = userMapper.toUserResponse(user);
        redisService.setData(username, userResponse, 300L);
        return userResponse;
    }

    @Override
    public UserResponse getUserDetails(String username) {
        UserResponse userResponse = redisService.getData(username, UserResponse.class);
        if (userResponse != null) {
            return userResponse;
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            return new UserException(String.format(USER_DATA_NOT_FOUND, username));
        });
        userResponse = userMapper.toUserResponse(user);
        redisService.setData(username, userResponse, 300L);
        return userResponse;
    }

    /**
     * This method is used to update the user details
     *
     * @param username
     * @param request
     * @return
     */
    @Transactional
    @Override
    public UserResponse updateUserDetails(String username, UserRequest request) {
        userValidation.validateUserRequest(request);
        User updatedUser = updateUserDataFromRequest(username, request);
        try {
            userRepository.updateUserDetails(updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getEmail(), updatedUser.getContactNumber(), username);
            log.info(String.format(LOG_USER_DETAILS_UPDATE_SUCCESS, username));
            redisService.deleteData(username);
            return getUserDetails();
        } catch (Exception exception) {
            log.error(String.format(LOG_USER_DETAIL_UPDATE_FAILED, username, exception.getMessage()));
            throw new UserException(String.format(UNABLE_TO_UPDATE_USER_DETAILS, username, exception.getMessage()));
        }
    }

    /**
     * This method is used to update the password
     *
     * @param username
     * @param password
     * @return
     */
    @Transactional
    @Override
    public UserResponse updatePassword(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            return new UserException(String.format(USER_DATA_NOT_FOUND, username));
        });
        try {
            userRepository.updatePassword(passwordEncoder.encode(password), user.getUsername());
            log.info(String.format(LOG_USER_PASSWORD_UPDATE_SUCCESS, username));
            redisService.deleteData(username);
            return getUserDetails();
        } catch (Exception exception) {
            log.error(String.format(LOG_UNABLE_TO_UPDATE_PASSWORD, username, exception.getMessage()));
            throw new UserException(String.format(UNABLE_TO_UPDATE_USER_PASSWORD, username, exception.getMessage()));
        }
    }

    /**
     * This method is used to update the user data from the request
     *
     * @param username
     * @param request
     * @return
     */
    private User updateUserDataFromRequest(String username, UserRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            return new UserException(String.format(USER_DATA_NOT_FOUND, username));
        });
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getContactNumber());
        user.setLocation(request.getLocation());
        user.setCompanyName(request.getCompanyName());
        user.setCompanyWebsite(request.getCompanyWebsite());
        return user;
    }
}
