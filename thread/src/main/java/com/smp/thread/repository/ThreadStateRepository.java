package com.smp.thread.repository;

import com.smp.thread.entity.ThreadState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThreadStateRepository extends JpaRepository<ThreadState, String> {
    Optional<ThreadState> findById(String id);
}