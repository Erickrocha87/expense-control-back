package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.LoginRequestDTO;
import com.rocha.expenditurecontrol.dtos.LoginResponseDTO;
import com.rocha.expenditurecontrol.dtos.AuthRequestDTO;
import com.rocha.expenditurecontrol.dtos.AuthResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.infra.security.TokenService;
import com.rocha.expenditurecontrol.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/register")

    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@Valid @RequestBody LoginRequestDTO request) {
        try{
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);
            User user = (User) authenticate.getPrincipal();
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok().body(new LoginResponseDTO(token));
        }catch (BadCredentialsException e){
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<AuthResponseDTO> updateUser(@Valid @RequestBody AuthRequestDTO user) {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }
}
