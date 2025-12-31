package com.tms.services.test;

import com.tms.services.NotificationService.EmailNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class testService {

    private final EmailNotificationService notificationService;

    public void sendEmail() {
        notificationService.sendEmail("nacimmakedhi@gmail.com", "Test email", "this is an email of test");
    }
}
