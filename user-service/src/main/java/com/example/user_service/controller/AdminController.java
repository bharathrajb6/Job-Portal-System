package com.example.user_service.controller;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public UserResponse getAdminDetails() {
        return userService.getUserDetails();
    }

    @RequestMapping(value = "/updateDetails/{username}", method = RequestMethod.PUT)
    public UserResponse updateAdminDetails(@PathVariable("username") String username, @RequestBody UserRequest request) {
        return userService.updateUserDetails(username, request);
    }

    @RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.PUT)
    public UserResponse updateAdminPassword(@PathVariable("username") String username, @RequestBody UserRequest request) {
        return userService.updatePassword(username, request.getPassword());
    }
}
