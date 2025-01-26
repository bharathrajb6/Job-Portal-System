package com.example.user_service.service.impl;

import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.exception.UserException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
}
