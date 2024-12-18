package com.smp.thread.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t2t")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message")
    private String message;
}