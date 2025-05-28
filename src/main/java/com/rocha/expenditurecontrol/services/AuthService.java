package com.rocha.expenditurecontrol.services;


import com.rocha.expenditurecontrol.dtos.auth.AuthRequestDTO;
import com.rocha.expenditurecontrol.dtos.auth.AuthResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.mapper.AuthMapper;
import com.rocha.expenditurecontrol.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User or password is incorrect"));
    }

    public AuthResponseDTO register (AuthRequestDTO request) {
        User user = mapper.mapToUser(request);
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return mapper.mapToResponse(user);
    }

}
