package com.smp.thread.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "thread_state")
public class ThreadState {

    @Id
    private String id = "MESSAGE_THREAD";

    @Column(name = "is_running", nullable = false)
    private boolean running;

    public ThreadState() {
        this.running = false;
    }

}
