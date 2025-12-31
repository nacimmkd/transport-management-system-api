package com.tms.services.NotificationService;

public interface INotificationService {
    void sendEmail(String to, String subject, String content);
}
