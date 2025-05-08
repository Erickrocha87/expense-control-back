package com.rocha.expenditurecontrol;

import com.rocha.expenditurecontrol.entities.Notification;
import com.rocha.expenditurecontrol.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ExpendituremanagementApplication{

	public static void main(String[] args) {
		SpringApplication.run(ExpendituremanagementApplication.class, args);
	}
}
