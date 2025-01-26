package com.example.user_service.dto.response;

import com.example.user_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String contactNumber;
    private Role role;

}
