package com.telstra.service;

import com.telstra.dto.UpdateProfileRequest;
import com.telstra.dto.UserProfileResponse;
import com.telstra.dto.UserResponse;
import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User u : users)
            userResponses.add(mapper.mapUserMin(u));
        return userResponses;
    }

    public List<UserResponse> findUser(String searchRequest) {


        String[] splited = searchRequest.split("\\s+");
        String searchQuery = "";
        for (String s : splited) {
            searchQuery += s + "* ";
        }
        List<User> uList = userRepository.findByUsername(searchQuery);
        List<UserResponse> sorted = new ArrayList<>();


        for (int i = 0; i < Math.min(uList.size(), 10); i++) {
            sorted.add(mapper.mapUserMin(uList.get(i)));
        }
        return sorted;
    }


    public UserProfileResponse userProfile(Long id) {
        return mapper.mapUser(userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("No user found with id : " + id)
        ));
    }


    //Increments users points based on id
    public String incrementUserPoints(Long id, Long points) {
        User u = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("No user found with id : " + id)
        );
        if (u.getPoints() == null) {
            u.setPoints(0L);
        }
        u.setPoints(u.getPoints() + points);
        userRepository.save(u);

        return "User " + u.getUsername() + "'s points got increased by " + points;
    }

    public boolean updateUserProfile(UpdateProfileRequest updateProfileRequest) {

        if (!updateProfileRequest.getEmail().equals(authService.getCurrentUser().getEmail())) {
            return false;
        }
        User u = userRepository.findByEmail(updateProfileRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("No user found with email : " + updateProfileRequest.getEmail())
        );

        if (updateProfileRequest.getUsername() != null && updateProfileRequest.getUsername().length() != 0) {
            u.setUsername(updateProfileRequest.getUsername());
        }
        if (updateProfileRequest.getLinkedin() != null && updateProfileRequest.getLinkedin().length() != 0) {
            u.setLinkedin(updateProfileRequest.getLinkedin());
        }
        if (updateProfileRequest.getGithub() != null && updateProfileRequest.getGithub().length() != 0) {
            u.setGithub(updateProfileRequest.getGithub());
        }

        if (updateProfileRequest.getN_password() != null && updateProfileRequest.getN_password().length() != 0) {

            if (passwordEncoder.matches(updateProfileRequest.getE_password(), u.getPassword())) {
                u.setPassword(passwordEncoder.encode(updateProfileRequest.getN_password()));
            } else return false;
        }
        userRepository.save(u);

        return true;
    }
}
