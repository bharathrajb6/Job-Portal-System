package com.example.job_listing_service.service;

import com.example.job_listing_service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service")
public interface UserService {
    @RequestMapping(value = "/api/v1/user/details/{username}", method = RequestMethod.GET)
    UserResponse getUserDetails(@PathVariable String username);
}
