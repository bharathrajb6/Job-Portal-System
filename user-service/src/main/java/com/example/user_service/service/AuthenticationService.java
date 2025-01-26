package com.example.user_service.service;

import com.example.user_service.dto.request.UserRequest;

public interface AuthenticationService {

    String register(UserRequest request);

    String login(UserRequest request);
}
