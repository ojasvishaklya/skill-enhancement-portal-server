package com.telstra.controller;


import com.telstra.dto.UpdateProfileRequest;
import com.telstra.dto.UserProfileResponse;
import com.telstra.dto.UserResponse;
import com.telstra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/search/{username}")
    public List<UserResponse> findUser(@PathVariable String username) {
        return userService.findUser(username);
    }

    @GetMapping("/users/profile/{id}")
    public UserProfileResponse userProfile(@PathVariable Long id) {
        return userService.userProfile(id);
    }

    @PostMapping("/users/update/")
    public ResponseEntity<String> updateUserProfile(@PathVariable UpdateProfileRequest updateProfileRequest) {
        System.out.println("in controller===============================================");

        return userService.updateUserProfile(updateProfileRequest) ?
                ResponseEntity.status(HttpStatus.OK).body("Profile updated successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Credentials")
                ;
    }

}
