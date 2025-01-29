package com.example.user_service.service.impl;

import com.example.user_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.user_service.messages.UserExceptionMessages.USER_DATA_NOT_FOUND;
import static com.example.user_service.messages.UserLogMessages.LOG_USER_DATA_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Load user data by username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(String.format(LOG_USER_DATA_NOT_FOUND, username));
            return new UsernameNotFoundException(String.format(USER_DATA_NOT_FOUND, username));
        });
    }
}
