package com.tms.notification;

public interface INotificationService {
    void sendEmail(String to, String subject, String content);
}
