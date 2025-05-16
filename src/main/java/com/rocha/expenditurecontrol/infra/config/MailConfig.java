package com.rocha.expenditurecontrol.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

@Configuration
public class MailConfig {

    private final String username;

    private final String password;

    public MailConfig(){
        Dotenv dotenv = Dotenv.load();
        this.username = dotenv.get("MAIL_USERNAME");
        this.password = dotenv.get("MAIL_PASSWORD");
    }

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("sandbox.smtp.mailtrap.io");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
