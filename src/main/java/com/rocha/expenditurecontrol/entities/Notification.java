package com.rocha.expenditurecontrol.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "to_user")
    @NotBlank(message = "User is required")
    private String toUser;

    private String subject;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "send_at")
    @CreationTimestamp
    private LocalDateTime sendAt;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Subscription subscription;


    public Notification(String toUser, String subject, String message, Status status, LocalDateTime sendAt) {
        this.toUser = toUser;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.sendAt = sendAt;
    }
}
