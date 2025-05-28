package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.EmailDTO;
import com.rocha.expenditurecontrol.entities.Notification;
import com.rocha.expenditurecontrol.entities.TokenChangePassword;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.entities.enums.NotificationStatus;
import com.rocha.expenditurecontrol.infra.security.TokenService;
import com.rocha.expenditurecontrol.services.NotificationService;
import com.rocha.expenditurecontrol.services.TokenChangePasswordService;
import com.rocha.expenditurecontrol.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;
    private final TokenChangePasswordService tokenPasswordService;

    @PostMapping("/send-token")
    public ResponseEntity<Map<String, String>> generateToken (@Valid @RequestBody EmailDTO dto){
        String email = dto.email();
        if (email == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<User> user = userService.findUserByEmail(email);

        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LocalDateTime timestamp = LocalDateTime.now();
        TokenChangePassword token = this.tokenPasswordService.verifyAndGenerateToken();
        notificationService.sendTokenEmail(new Notification(user.get().getEmail(),
                "Código de redefinição de senha",
                "Seu código para recuperar a senha vai expirar em 10 minutos. " + token.getToken(),
                NotificationStatus.SENT,
                timestamp
        ));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message", "Token generated successfully"));
    }
}
