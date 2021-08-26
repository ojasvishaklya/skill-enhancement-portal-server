package com.telstra.controller;


import com.telstra.dto.RegisterRequest;
import com.telstra.dto.SigninRequest;
import com.telstra.dto.SigninResponse;
import com.telstra.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
        authService.signUp(registerRequest);
        return new ResponseEntity<>("user registered successfully", HttpStatus.OK);
    }

    @PostMapping("/auth/signin")
    public SigninResponse signIn(@RequestBody SigninRequest signinRequest) {
        return authService.singIn(signinRequest);
    }
}
