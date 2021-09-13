package com.telstra.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String username;
    private String e_password;
    private String n_password;
    private String linkedin;
    private String github;
    private String email;
}
