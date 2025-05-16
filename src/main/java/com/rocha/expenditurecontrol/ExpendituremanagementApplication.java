package com.rocha.expenditurecontrol;

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
