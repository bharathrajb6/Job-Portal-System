package com.example.user_service.service.impl;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.exception.UserException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
        User user = userRepository.findByUsername(getUsername()).orElse(null);
        if (user != null) {
            return userMapper.toUserResponse(user);
        }
        throw new UserException("User not found");
    }

    @Override
    public UserResponse updateUserDetails(String username, UserRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserException("User not found");
        });
        user = updateUserDataFromRequest(user, request);

        try {
            userRepository.updateUserDetails(user.getFirstName(), user.getLastName(), user.getEmail(), user.getContactNumber(), username);
        } catch (Exception exception) {
            throw new UserException(exception.getMessage());
        }
        return getUserDetails();
    }

    @Override
    public UserResponse updatePassword(String username, String password) {
        try {
            userRepository.updatePassword(passwordEncoder.encode(password), username);
        } catch (Exception exception) {
            throw new UserException(exception.getMessage());
        }
        return getUserDetails();
    }

    private User updateUserDataFromRequest(User user, UserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getContactNumber());
        return user;
    }
}
