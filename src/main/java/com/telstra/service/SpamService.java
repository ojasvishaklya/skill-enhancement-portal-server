package com.telstra.service;

import com.telstra.model.Spam;
import com.telstra.model.User;
import com.telstra.repository.SpamRepository;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public String reportSpam(Long s_id) {
        Spam spam = new Spam();
        spam.setS_id(s_id);
        spam.setU_id(authService.getCurrentUser().getUserId());

        spamRepository.save(spam);
        return "Successfully spammed " + s_id;
    }

    public List<Long> getSpam(Long id) {
        List<Long> spamedBy = new ArrayList<Long>();
        List<Spam> spams = spamRepository.findAll();
        for (Spam s : spams) {
            if (s.getS_id() == id)
                spamedBy.add(s.getU_id());
        }
        if(spamedBy.size()>=20){
            User u= userRepository.findById(id).get();
            u.setEnabled(false);
        }
        return spamedBy;
    }
}
