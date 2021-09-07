package com.telstra.service;

import com.telstra.dto.UserProfileResponse;
import com.telstra.dto.UserResponse;
import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    GetterSource getterSource;
    @Autowired
    Mapper mapper;

    public List<UserResponse> getAllUsers() {
        List<User> users=userRepository.findAll();
        List<UserResponse>userResponses=new ArrayList<>();
        for(User u : users)
            userResponses.add(mapper.mapUserMin(u));
        return userResponses;
    }

    public UserProfileResponse findUser(String username) {
        return mapper.mapUser(userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("No user found with username : "+username)
        ));
    }


    public UserProfileResponse userProfile(Long id) {
        return mapper.mapUser(userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("No user found with id : "+id)
        ));
    }


    //Increments users points based on id
    public String incrementUserPoints(Long id, Long points) {
        User u = userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("No user found with id : "+id)
        );
        if (u.getPoints() == null) {
            u.setPoints(0L);
        }
        u.setPoints(u.getPoints() + points);
        userRepository.save(u);

        return "User " + u.getUsername() + "'s points got increased by " + points;
    }
}
