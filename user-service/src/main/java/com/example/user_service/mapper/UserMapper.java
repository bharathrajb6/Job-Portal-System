package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * This method is used to map the user request to user
     *
     * @param request
     * @return
     */
    User toUser(UserRequest request);

    /**
     * This method is used to map the user to user response
     *
     * @param user
     * @return
     */
    UserResponse toUserResponse(User user);
}
