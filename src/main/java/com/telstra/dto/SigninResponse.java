package com.telstra.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponse {
    private String id;
    private String username;
    private String authToken;
    private String refreshToken;
    private String expiresAt;
}
