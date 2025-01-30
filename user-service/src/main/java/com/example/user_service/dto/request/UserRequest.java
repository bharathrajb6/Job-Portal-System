package com.example.user_service.dto.request;

import com.example.user_service.model.AccountStatus;
import com.example.user_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    private Role role;
    private String location;
    private boolean fresher;
    private String companyName;
    private String companyWebsite;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private AccountStatus accountStatus;
}
