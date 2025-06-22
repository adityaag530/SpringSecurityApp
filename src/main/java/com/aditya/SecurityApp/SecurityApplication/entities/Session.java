package com.aditya.SecurityApp.SecurityApplication.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
 * @author adityagupta
 * @date 21/06/25
 */
@Entity
@Data
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String refreshToken;

    @CreationTimestamp
    public LocalDateTime lastUsedAt;

    @ManyToOne
    public User user;
}

