package com.example.user_service.service;

import com.example.user_service.model.User;

public interface TokenService {

    void saveUserToken(String jwt_token, User user);

    void revokeAllTokensForUser(String username);
}
