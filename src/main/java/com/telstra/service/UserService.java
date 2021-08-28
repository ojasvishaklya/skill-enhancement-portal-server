package com.telstra.service;

import com.telstra.dto.UserProfileResponse;
import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
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
    public String incrementUserPoints(Long id ,Long points){
        User u=userRepository.findById(id).get();
        if(u.getPoints()==null){
            u.setPoints(0L);
        }
        u.setPoints(u.getPoints()+points);
        userRepository.save(u);

        return "User "+u.getUsername()+"'s points got increased by "+points;
    }
}
