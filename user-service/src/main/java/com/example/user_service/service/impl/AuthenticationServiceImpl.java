package com.example.user_service.service.impl;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.exception.UserException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.Token;
import com.example.user_service.model.User;
import com.example.user_service.repo.TokenRepository;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * This method is used to register the user
     *
     * @param request
     * @return
     */
    @Override
    public String register(UserRequest request) {

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            userRepository.save(user);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(jwtToken, user);
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
        Authentication authentication;
        try {
            // Authenticate the user with username and password
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception exception) {
            // If login credentials are wrong, then throw the exception
            authentication = null;
            log.error("USER_DATA_NOT_FOUND", request.getUsername());
            throw new UserException(String.format("USER_NOT_FOUND", request.getUsername()));
        }
        // Fetch the user details
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null && authentication != null) {
            // Generate a new token
            String jwtToken = jwtService.generateToken(user);
            // Delete all the existing token which is generated for this user
            revokeAllTokensForUser(user.getUsername());
            // Save the new token
            saveUserToken(jwtToken, user);
            return jwtToken;
        } else {
            return null;
        }
    }

    /**
     * This method is used to log out the user
     *
     * @param jwt_token
     * @param user
     */
    @Transactional
    private void saveUserToken(String jwt_token, User user) {

        Token token = new Token();
        token.setTokenID(String.valueOf(UUID.randomUUID()));
        token.setJwtToken(jwt_token);
        token.setUser(user);
        token.setLoggedOut(false);

        try {
            // Save the new token
            tokenRepository.save(token);
            log.info("JWT_TOKEN_FOR_USER_SAVED_SUCCESS");
        } catch (Exception exception) {
            // If any issue come, then throw the exception
            log.error(String.format("UNABLE_TO_SAVE_JWT_TOKEN_TO_DB", exception.getMessage()));
            throw new UserException(String.format("UNABLE_TO_SAVE_JWT_TOKEN", exception.getMessage()));
        }
    }

    /**
     * This method is used to revoke all the tokens for the user
     *
     * @param username
     */
    @Transactional
    private void revokeAllTokensForUser(String username) {
        // Fetch all the tokens for this username
        List<Token> tokenList = tokenRepository.findAllTokens(username);
        if (!tokenList.isEmpty()) {
            tokenList.forEach(t -> t.setLoggedOut(true));
        }
        try {
            // Delete all the tokens from database
            tokenRepository.deleteAll(tokenList);
            log.info("JWT_TOKEN_FOR_USER_DELETED_SUCCESS");
        } catch (Exception exception) {
            // If any issue come, then throw the exception
            String message = String.format("UNABLE_TO_DELETE_ALL_OLD_TOKENS", username, exception.getMessage());
            log.error(message);
            throw new UserException(message);
        }
    }
}
