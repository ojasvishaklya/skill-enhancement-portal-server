package com.telstra.controller;


import com.telstra.model.User;
import com.telstra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public Optional<User> findUser(@PathVariable String username){
        return userService.findUser(username);
    }

    @PutMapping("/user/{id}/spam")
    public User spamUser(@PathVariable Long id){
        return userService.spamUser(id);
    }
}
