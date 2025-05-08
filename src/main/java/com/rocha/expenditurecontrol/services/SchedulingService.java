package com.rocha.expenditurecontrol.services;

import com.rocha.expenditurecontrol.entities.Notification;
import com.rocha.expenditurecontrol.entities.Status;
import com.rocha.expenditurecontrol.entities.Subscription;
import com.rocha.expenditurecontrol.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final NotificationService notificationService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 8 * * *")
    public void verifyExpiredSubscriptions() {

        List<User> users = userService.getAllUsers();
        LocalDateTime timestamp = LocalDateTime.now();

        for (User user : users) {
            List<Subscription> subscriptions = subscriptionService.getExpiringSubscriptionsForUser(user);
            for (Subscription sub : subscriptions) {
                Notification notify = new Notification(
                        user.getEmail(),
                        "Assinatura prestes a vencer!",
                        "A assinatura " + sub.getServiceName() + " no valor de: R$" + sub.getPrice() + " vence hoje. Considere pagar ou cancelar.",
                        Status.SENT,
                        timestamp);
                notify.setSubscription(sub);
                notificationService.sendNotification(notify);
            }
        }
    }

}
