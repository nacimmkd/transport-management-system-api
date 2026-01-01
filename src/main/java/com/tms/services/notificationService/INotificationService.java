package com.tms.services.notificationService;

public interface INotificationService {
    void sendEmail(String to, String subject, String content);
}
