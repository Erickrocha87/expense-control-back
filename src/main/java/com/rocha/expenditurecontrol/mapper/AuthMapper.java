package com.rocha.expenditurecontrol.mapper;

import com.rocha.expenditurecontrol.dtos.auth.AuthRequestDTO;
import com.rocha.expenditurecontrol.dtos.auth.AuthResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User mapToUser(AuthRequestDTO request){
        return new User(
                request.username(),
                request.password(),
                request.email());
    };

    public AuthResponseDTO mapToResponse(User user){
        return new AuthResponseDTO(
                user.getId(),
                user.getUsername()
        );
    }


}
