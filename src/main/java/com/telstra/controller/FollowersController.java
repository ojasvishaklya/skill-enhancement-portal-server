package com.telstra.controller;


import com.telstra.dto.UserResponse;
import com.telstra.service.FollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FollowersController {
    @Autowired
    FollowersService followersService;

    @PostMapping("/follow/{sId}")
    public String follow(@PathVariable Long sId) {
        return followersService.follow(sId);
    }

    @GetMapping("/followers/{id}")
    public List<UserResponse> getFollowers(@PathVariable Long id) {
        return followersService.getFollowers(id);
    }

    @GetMapping("/following/{id}")
    public List<UserResponse> getFollowing(@PathVariable Long id) {
        return followersService.getFollowing(id);
    }
}
