package com.rocha.expenditurecontrol.services;

import com.rocha.expenditurecontrol.entities.TokenChangePassword;
import com.rocha.expenditurecontrol.repositories.TokenChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenChangePasswordService {

    private final TokenChangeRepository tokenRepository;
    private final String CHARACRERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int TOKEN_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public TokenChangePassword verifyAndGenerateToken() {
        String token;
        do {
            token = generateSecurityToken();
        }
        while (tokenRepository.findByToken(token) != null);

        TokenChangePassword tokenChange = new TokenChangePassword();
        tokenChange.setToken(token);
        tokenChange.setExpired(false);
        return tokenRepository.save(tokenChange);
    }

    public TokenChangePassword findByToken(String tokenRequest) {
        TokenChangePassword token = tokenRepository.findByToken(tokenRequest);
        return token;
    }

    public String generateSecurityToken() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++){
            int index = random.nextInt(CHARACRERS.length());
            token.append(CHARACRERS.charAt(index));
        }
        return token.toString();
    }

    public void expiredToken(String token) {
        TokenChangePassword verifyToken = tokenRepository.findByToken(token);
        if (verifyToken == null) {
            throw new RuntimeException("Token not found");
        }
        LocalDateTime expirationTime = verifyToken.getCreatedAt().plusMinutes(10);
        if (LocalDateTime.now().isAfter(expirationTime)) {
            verifyToken.setExpired(true);
            tokenRepository.save(verifyToken);
        }
    }
}
