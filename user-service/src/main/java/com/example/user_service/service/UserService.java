package com.example.user_service.service;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;

public interface UserService {

    UserResponse getUserDetails();

    UserResponse updateUserDetails(String username, UserRequest request);

    UserResponse updatePassword(String username, String password);
}
