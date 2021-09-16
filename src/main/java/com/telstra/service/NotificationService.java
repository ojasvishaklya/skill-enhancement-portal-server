package com.telstra.service;

import com.telstra.dto.NotificationResponse;
import com.telstra.model.Notification;
import com.telstra.repository.NotificationRepository;
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
        Notification notification = new Notification();
        notification.setNotification(message);
        notification.setInstant(Instant.now());
        notification.setUserId(u_id);

        notificationRepository.save(notification);
        return "Successfully sent notification to " + u_id;
    }

    public List<NotificationResponse> getNotifications(Long id) {
        List<NotificationResponse> myNotifications = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findAll();
        for (Notification n : notifications) {
            if (n.getUserId().equals(id))
                myNotifications.add(mapper.mapNotification(n));
        }
        myNotifications.sort((a, b) -> {
            return b.getInstant().compareTo(a.getInstant());
        });
        List<NotificationResponse> latestNotifications = new ArrayList<>();

        for(int i=0;i<Math.min(myNotifications.size(),5);i++){
            latestNotifications.add(myNotifications.get(i));
        }
        return latestNotifications;
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
