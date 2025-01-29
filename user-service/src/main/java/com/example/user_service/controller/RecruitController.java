package com.example.user_service.controller;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /**
     * This method is used to update recruiter details
     *
     * @param username
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateDetails/{username}", method = RequestMethod.PUT)
    public UserResponse updateRecruiterDetails(@PathVariable("username") String username, @RequestBody UserRequest request) {
        return userService.updateUserDetails(username, request);
    }

    /**
     * This method is used to update recruiter password
     *
     * @param username
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.PUT)
    public UserResponse updateRecruiterPassword(@PathVariable("username") String username, @RequestBody UserRequest request) {
        return userService.updatePassword(username, request.getPassword());
    }
}
