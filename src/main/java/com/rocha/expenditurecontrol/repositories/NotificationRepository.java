package com.rocha.expenditurecontrol.repositories;

import com.rocha.expenditurecontrol.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
