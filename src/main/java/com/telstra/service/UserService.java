package com.telstra.service;

import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

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
}
