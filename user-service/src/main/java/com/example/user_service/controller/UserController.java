package com.example.user_service.controller;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * This method is used to get user details
     *
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public UserResponse getUserDetails() {
        return userService.getUserDetails();
    }

    @RequestMapping(value = "/updateDetails/{username}", method = RequestMethod.PUT)
    public UserResponse updateUserDetails(@PathVariable("username") String username, @RequestBody UserRequest userRequest) {
        return userService.updateUserDetails(username, userRequest);
    }

    @RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.PUT)
    public UserResponse updateUserPassword(@PathVariable("username") String username, @RequestBody UserRequest userRequest) {
        return userService.updatePassword(username, userRequest.getPassword());
    }
}
