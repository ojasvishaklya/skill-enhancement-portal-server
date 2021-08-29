package com.telstra.service;


import com.telstra.dto.*;
import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import com.telstra.security.JwtSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtSource jwtSource;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public SigninResponse signIn(SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinRequest.getUsername(),
                        signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtSource.generateToken(authentication);
        return new SigninResponse(signinRequest.getUsername(), token,
                refreshTokenService.generateRefreshToken().getToken()
                , Instant.now().plusMillis(jwtSource.getJwtExpirationInMillis()).toString());

    }

    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtSource.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtSource.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

//    public boolean isLoggedIn() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
//    }
}
