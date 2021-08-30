package com.telstra.controller;


import com.telstra.dto.NotificationResponse;
import com.telstra.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public List<NotificationResponse> getSpam(@PathVariable Long id) {
        return notificationService.getNotifications(id);
    }

    @DeleteMapping("/notification/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
