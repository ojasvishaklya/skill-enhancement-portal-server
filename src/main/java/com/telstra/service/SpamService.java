package com.telstra.service;

import com.telstra.model.Spam;
import com.telstra.repository.SpamRepository;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SpamService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    SpamRepository spamRepository;
    @Autowired
    GetterSource getterSource;

    public String reportSpam(Long s_id) {
        List<Long> spamList = getterSource.getUserSpamList(s_id);
        for(Long i : spamList){
            if(i.equals(authService.getCurrentUser().getUserId())){
                return "You have already spammed this user";
            }
        }

        Spam spam = new Spam();

        spam.setS_id(s_id);

        spam.setU_id(authService.getCurrentUser().getUserId());
        spamRepository.save(spam);
        return "Successfully spammed " + s_id;
    }

    public List<Long> getSpam(Long id) {
        return getterSource.getUserSpamList(id);
    }
}
