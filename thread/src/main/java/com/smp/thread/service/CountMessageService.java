package com.smp.thread.service;

import com.smp.thread.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class CountMessageService {

    @Autowired
    private MessageRepository messageRepository;

    private long lastCount = 0;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public void countMessages() {
        while (isRunning.get()) {
            long count = messageRepository.count();
            if (count != lastCount) {
                log.info("Количество записей в таблице: {}", count);
                lastCount = count;
            }
            sleep();
        }
    }

    public void start() {
        isRunning.set(true);
        countMessages();
    }

    public void stop() {
        isRunning.set(false);
    }

    private void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}