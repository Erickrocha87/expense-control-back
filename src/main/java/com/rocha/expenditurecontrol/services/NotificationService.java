package com.rocha.expenditurecontrol.services;

import com.rocha.expenditurecontrol.entities.Notification;
import com.rocha.expenditurecontrol.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService{

    private final JavaMailSender mailSender;

    public void sendNotification(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getToUser());
        message.setFrom("no-reply@rocha.com");
        message.setSubject(notification.getSubject());
        message.setText(notification.getMessage());
        mailSender.send(message);
        System.out.println(notification);
        System.out.println(message);
    }

}
