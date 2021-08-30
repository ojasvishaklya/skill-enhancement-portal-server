package com.telstra.controller;


import com.telstra.dto.UserProfileResponse;
import com.telstra.dto.UserResponse;
import com.telstra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public UserProfileResponse findUser(@PathVariable String username) {
        return userService.findUser(username);
    }

    @GetMapping("/users/{id}/profile")
    public UserProfileResponse userProfile(@PathVariable Long id) {
        return userService.userProfile(id);
    }

}
