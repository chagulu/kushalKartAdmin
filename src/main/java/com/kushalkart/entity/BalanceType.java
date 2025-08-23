package com.kushalkart.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.kushalkart.entity.TransactionType;

@Entity
@Table(name = "balance_type")
public class BalanceType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    // getters/setters
}


