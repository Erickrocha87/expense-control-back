package com.rocha.expenditurecontrol.mapper;

import com.rocha.expenditurecontrol.dtos.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.dtos.UserResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import lombok.Builder;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
@Builder
public class UserMapper {

    public UserResponseDTO toDTO(User user) {

        List<SubscriptionResponseDTO> subscriptions = user.getSubscriptions().stream()
                .map(SubscriptionMapper::toSubscriptionResponseDTO)
                .toList();

        return new UserResponseDTO(user.getId(), user.getEmail(), subscriptions);
    }
}