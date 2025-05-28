package com.rocha.expenditurecontrol.mapper;

import com.rocha.expenditurecontrol.dtos.user.UserRequestDTO;
import com.rocha.expenditurecontrol.dtos.user.UserResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import lombok.Builder;
import lombok.experimental.UtilityClass;

@UtilityClass
@Builder
public class UserMapper {

    public User toEntity(UserRequestDTO user) {
        return new User(
                user.username(),
                user.email()
        );
    }

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImageUrl()
        );
    }

}