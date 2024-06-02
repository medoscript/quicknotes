package com.example.quicknotes.security.sec_service;

import com.example.quicknotes.model.User;
import com.example.quicknotes.security.security_dto.TokenResponseDto;
import com.example.quicknotes.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private UserService userService;
    private TokenService tokenService;
    private Map<String, String> refreshStorage;
    private BCryptPasswordEncoder encoder;

    public AuthService(UserService userService, TokenService tokenService, BCryptPasswordEncoder encoder) {
        this.userService = (UserService) userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.refreshStorage = new HashMap<>();
    }

    public TokenResponseDto login(@Nonnull User inboundUser) throws AuthException {
        String username = inboundUser.getUsername();
        User foundUser = (User) userService.loadUserByUsername(username);

        if (encoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Password is incorrect");
        }
    }

    public TokenResponseDto getAccessToken(@Nonnull String refreshToken) {
        if (tokenService.validateRefreshToken(refreshToken)) {
            Claims refreshClaims = tokenService.getRefreshClaims(refreshToken);
            String username = refreshClaims.getSubject();
            String savedRefreshToken = refreshStorage.get(username);

            if (refreshToken.equals(savedRefreshToken)) {
                User user = (User) userService.loadUserByUsername(username);
                String accessToken = tokenService.generateAccessToken(user);
                return new TokenResponseDto(accessToken, null);
            }
        }
        throw new RuntimeException("Refresh token is incorrect");
    }
}
