package com.telstra.service;

import com.telstra.dto.UserProfileResponse;
import com.telstra.dto.UserResponse;
import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<UserResponse>userResponses=new ArrayList<UserResponse>();
        for(User u : users)
            userResponses.add(mapper.mapUserMin(u));
        return userResponses;
    }

    public UserProfileResponse findUser(String username) {
        return mapper.mapUser(userRepository.findByUsername(username).get());
    }

    public User spamUser(Long id) {
        return userRepository.findById(id).get();
//        User user= userRepository.findById(id).get();
//        Set<Long> spam=user.getSpam();
//        spam.add(authService.getCurrentUser().getUserId());
//        if(spam.size()>=30){
//            user.setEnabled(false);
//        }
//        user.setSpam(spam);
//        return userRepository.save(user);

    }

    public UserProfileResponse userProfile(Long id) {
        return mapper.mapUser(userRepository.findById(id).get());
    }


    //Increments users points based on id
    public String incrementUserPoints(Long id, Long points) {
        User u = userRepository.findById(id).get();
        if (u.getPoints() == null) {
            u.setPoints(0L);
        }
        u.setPoints(u.getPoints() + points);
        userRepository.save(u);

        return "User " + u.getUsername() + "'s points got increased by " + points;
    }
}
