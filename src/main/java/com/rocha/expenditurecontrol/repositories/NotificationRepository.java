package com.rocha.expenditurecontrol.repositories;

import com.rocha.expenditurecontrol.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
