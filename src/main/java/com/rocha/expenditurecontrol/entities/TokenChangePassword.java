package com.rocha.expenditurecontrol.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_token_change_password")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenChangePassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String token;

    @CreationTimestamp
    @Column(name = "created_At")
    LocalDateTime createdAt;

    Boolean expired;

}
