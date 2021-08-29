package com.telstra.service;

import com.telstra.dto.NotificationResponse;
import com.telstra.model.Notification;
import com.telstra.model.Spam;
import com.telstra.model.User;
import com.telstra.repository.NotificationRepository;
import com.telstra.repository.SpamRepository;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    Mapper mapper;

    public String sendNotification(Long u_id, String message) {
        Notification notification=new Notification();
        notification.setNotification(message);
        notification.setInstant(Instant.now());
        notification.setUserId(u_id);

        notificationRepository.save(notification);
        return "Successfully sent notification to " + u_id;
    }

    public List<NotificationResponse> getNotifications(Long id) {
        List<NotificationResponse> myNotifications = new ArrayList<NotificationResponse>();
        List<Notification> notifications = notificationRepository.findAll();
        for (Notification n : notifications) {
            if (n.getUserId() == id)
                myNotifications.add(mapper.mapNotification(n));
        }
        return myNotifications;
    }
    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }
}
