package com.example.user_service.controller;

import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruiter")
@RequiredArgsConstructor
public class RecruitController {

    private final UserService userService;

    /**
     * This method is used to get recruiter details
     *
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public UserResponse getRecruiterDetails() {
        return userService.getUserDetails();
    }
}
