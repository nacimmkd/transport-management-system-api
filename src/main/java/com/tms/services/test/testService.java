package com.tms.services.test;

import com.tms.services.NotificationService.EmailNotificationService;
import com.tms.services.NotificationService.EmailTemplates;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class testService {

    private final EmailNotificationService emailNotificationService;

    public void sendEmail() {
        var template = EmailTemplates.getEmailCredential("Nadjib" , "nassimmakdi2001@gmail.com","123");
        emailNotificationService.sendEmail("nassimmakdi2001@gmail.com","Email de Test", template);
    }

}
