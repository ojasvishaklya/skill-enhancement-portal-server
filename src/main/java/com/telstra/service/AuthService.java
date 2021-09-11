package com.telstra.service;


import com.telstra.dto.*;
import com.telstra.exceptions.UserNotFoundException;
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
    public String signUp(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return "Email already exists";
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setPoints(0L);
        user.setGithub(registerRequest.getGithub());
        user.setLinkedin(registerRequest.getLinkedin());
        userRepository.save(user);
        return "User Created Successfully";
    }

    @Transactional
    public SigninResponse signIn(SigninRequest signinRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signinRequest.getEmail(),
                            signinRequest.getPassword()
                    )
            );
            System.out.println(authentication.isAuthenticated()+"============");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtSource.generateToken(authentication);

            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User u= userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()->{
               return new UserNotFoundException("User not found");
            });
            return new SigninResponse(u.getUserId().toString(),u.getUsername(), token,
                    refreshTokenService.generateRefreshToken().getToken()
                    , Instant.now().plusMillis(jwtSource.getJwtExpirationInMillis()).toString());
        }catch (Exception e){
            System.out.println("e.getMessage()");
        }


    return null;
    }


    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername())
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

}
