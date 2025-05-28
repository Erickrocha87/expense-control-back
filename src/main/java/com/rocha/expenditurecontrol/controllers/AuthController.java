package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.*;
import com.rocha.expenditurecontrol.entities.TokenChangePassword;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.infra.security.TokenService;
import com.rocha.expenditurecontrol.repositories.TokenChangeRepository;
import com.rocha.expenditurecontrol.repositories.UserRepository;
import com.rocha.expenditurecontrol.services.AuthService;
import com.rocha.expenditurecontrol.services.TokenChangePasswordService;
import com.rocha.expenditurecontrol.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final TokenChangePasswordService tokenPasswordService;
    private final TokenChangeRepository tokenChangeRepository;

    @PostMapping("/register")

    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
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

    @PutMapping("/users/change-password")
    public ResponseEntity<Map<String,String>> changePassword(@Valid @RequestBody ChangePasswordDTO dto, @AuthenticationPrincipal JWTUserDataDTO user) {
        System.out.println(user);
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> userEntity = userService.findUserByEmail(user.email());
        if (!passwordEncoder.matches(dto.currentPassword(), userEntity.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Message", "Current password is incorrect"));
        }
        userEntity.get().setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(userEntity.get());
        return ResponseEntity.ok((Map.of("Message","Password changed successfully")));
    }

    @PutMapping("/forget-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody TokenAndPasswordDTO request ) {
        String token = request.token();
        TokenChangePassword verifyToken = this.tokenPasswordService.findByToken(token);
        Optional<User> user = userService.findUserByEmail(request.email());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (verifyToken != null && verifyToken.getToken().equals(token)){
            tokenPasswordService.expiredToken(token);
            if (verifyToken.getExpired()){
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            user.get().setPassword(passwordEncoder.encode(request.password()));
            userRepository.save(user.get());
            verifyToken.setExpired(true);
            tokenChangeRepository.save(verifyToken);
            return ResponseEntity.ok((Map.of("Message","Password changed successfully")));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
