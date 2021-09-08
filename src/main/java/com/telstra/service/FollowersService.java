package com.telstra.service;

import com.telstra.dto.UserResponse;
import com.telstra.model.Followers;
import com.telstra.repository.FollowersRepository;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FollowersService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    FollowersRepository followersRepository;
    @Autowired
    Mapper mapper;

    public String follow(Long s_id) {
        if (s_id.equals(authService.getCurrentUser().getUserId())) {
            return "cant follow yourself";
        }
        if (checkIfFollowing(authService.getCurrentUser().getUserId(), s_id)) {
            return "Already following";
        }
        Followers followers = new Followers();
        followers.setUserId(s_id);
        followers.setFollowerId(authService.getCurrentUser().getUserId());

        followersRepository.save(followers);
        return "You are now following " + s_id;
    }

    private boolean checkIfFollowing(Long myId, Long userId) {
        List<Followers> followers = followersRepository.findAll();
        for (Followers s : followers) {
            if (s.getFollowerId().equals(myId) && s.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public List<UserResponse> getFollowers(Long id) {
        List<UserResponse> followedBy = new ArrayList<>();
        List<Followers> followers = followersRepository.findAll();
        for (Followers s : followers) {
            if (s.getUserId().equals(id))
                followedBy.add(mapper.mapUserMin(userRepository.findById(s.getFollowerId()).orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                )));
        }
        return followedBy;
    }

    public List<UserResponse> getFollowing(Long id) {
        List<UserResponse> follows = new ArrayList<>();
        List<Followers> followers = followersRepository.findAll();
        for (Followers s : followers) {
            if (s.getFollowerId().equals(id))
                follows.add(mapper.mapUserMin(userRepository.findById(s.getUserId()).orElseThrow(
                        () -> new UsernameNotFoundException("No such user found")
                )));
        }
        return follows;
    }
}
