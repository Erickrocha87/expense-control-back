package com.rocha.expenditurecontrol.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionFrequency;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_subscription")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", length = 150)
    private String serviceName;

    private BigDecimal price;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionFrequency frequency;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;

    @OneToMany(mappedBy = "subscription")
    @JsonIgnore
    private List<Notification> notifications;

    public Subscription(Long id, String serviceName, BigDecimal price, LocalDate dueDate, SubscriptionFrequency frequency, SubscriptionStatus status, User user) {
        this.id = id;
        this.serviceName = serviceName;
        this.price = price;
        this.dueDate = dueDate;
        this.frequency = frequency;
        this.status = status;
        this.user = user;
    }
}
