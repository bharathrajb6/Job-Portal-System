package com.example.user_service.service.impl;

import com.example.user_service.exception.UserException;
import com.example.user_service.model.Token;
import com.example.user_service.model.User;
import com.example.user_service.repo.TokenRepository;
import com.example.user_service.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.example.user_service.messages.UserExceptionMessages.UNABLE_TO_DELETE_ALL_OLD_TOKENS;
import static com.example.user_service.messages.UserExceptionMessages.UNABLE_TO_SAVE_TOKEN;
import static com.example.user_service.messages.UserLogMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    /**
     * This method is used to log out the user
     *
     * @param jwt_token
     * @param user
     */
    @Transactional
    @Override
    public void saveUserToken(String jwt_token, User user) {

        Token token = new Token();
        token.setTokenID(String.valueOf(UUID.randomUUID()));
        token.setJwtToken(jwt_token);
        token.setUser(user);
        token.setLoggedOut(false);

        try {
            // Save the new token
            tokenRepository.save(token);
            log.info(LOG_TOKEN_SAVED);
        } catch (Exception exception) {
            // If any issue come, then throw the exception
            log.error(String.format(LOG_UNABLE_TO_SAVE_JWT_TOKEN_TO_DB, exception.getMessage()));
            throw new UserException(String.format(UNABLE_TO_SAVE_TOKEN, exception.getMessage()));
        }
    }

    /**
     * This method is used to revoke all the tokens for the user
     *
     * @param username
     */
    @Transactional
    @Override
    public void revokeAllTokensForUser(String username) {
        // Fetch all the tokens for this username
        List<Token> tokenList = tokenRepository.findAllTokens(username);
        if (!tokenList.isEmpty()) {
            tokenList.forEach(t -> t.setLoggedOut(true));
        }
        try {
            // Delete all the tokens from database
            tokenRepository.deleteAll(tokenList);
            log.info(String.format(LOG_JWT_TOKEN_FOR_USER_DELETED_SUCCESS, username));
        } catch (Exception exception) {
            // If any issue come, then throw the exception
            log.error(String.format(LOG_UNABLE_TO_DELETE_ALL_OLD_TOKENS, username, exception.getMessage()));
            throw new UserException(String.format(UNABLE_TO_DELETE_ALL_OLD_TOKENS, username, exception.getMessage()));
        }
    }
}
