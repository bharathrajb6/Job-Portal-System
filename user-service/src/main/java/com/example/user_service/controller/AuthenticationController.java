package com.example.user_service.controller;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * This method is used to register a user
     *
     * @param userRequest
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody UserRequest userRequest) {
        return authenticationService.register(userRequest);
    }

    /**
     * This method is used to log in a user
     *
     * @param userRequest
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserRequest userRequest) {
        return authenticationService.login(userRequest);
    }
}
