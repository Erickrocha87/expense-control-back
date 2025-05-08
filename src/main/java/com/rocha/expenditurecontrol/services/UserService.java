package com.rocha.expenditurecontrol.services;

import com.rocha.expenditurecontrol.dtos.AuthRequestDTO;
import com.rocha.expenditurecontrol.dtos.AuthResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.exceptions.UserNotFoundException;
import com.rocha.expenditurecontrol.infra.JWTUserData;
import com.rocha.expenditurecontrol.mapper.AuthMapper;
import com.rocha.expenditurecontrol.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthResponseDTO register (AuthRequestDTO request) {
        System.out.println("dados do usuario" + request);
        System.out.println(request.username() + " " + request.password() + " " + request.email());
        User user = mapper.mapToUser(request);
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return mapper.mapToResponse(user);
    }

    public AuthResponseDTO updateUser (AuthRequestDTO request) {
        User user = mapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return mapper.mapToResponse(user);
    }

    public User getAuthUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        JWTUserData data = (JWTUserData) auth.getPrincipal();
        String email = data.email();
        Long id = data.id();
        UserDetails userVerify = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found. Id: " + id));
        return (User)userVerify;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
