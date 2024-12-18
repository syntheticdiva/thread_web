package com.smp.thread.service;

import com.smp.thread.entity.MessageEntity;
import com.smp.thread.exception.MessageServiceException;
import com.smp.thread.repository.MessageRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private volatile boolean isRunning = false;
    private static final int BATCH_SIZE = 100;
    private static final long SLEEP_DURATION = 200;
    private long lastCount = 0;

    public void startInsertMessages() {
        if (!isRunning) {
            isRunning = true;
            executorService.submit(this::insertMessages);
            executorService.submit(this::countMessages);
        }
    }

    public void stopInsertMessages() {
        isRunning = false;
        executorService.shutdownNow();
    }

    private void insertMessages() {
        while (isRunning) {
            List<MessageEntity> messages = createMessages();
            try {
                messageRepository.saveAll(messages);
            } catch (RuntimeException e) {
                throw new MessageServiceException("Ошибка при сохранении сообщений", e);
            }
            sleep();
        }
    }

    private List<MessageEntity> createMessages() {
        List<MessageEntity> messages = new ArrayList<>();
        for (int i = 0; i < BATCH_SIZE; i++) {
            MessageEntity message = new MessageEntity();
            message.setMessage("Message " + (i + 1));
            messages.add(message);
        }
        return messages;
    }

    private void countMessages() {
        while (isRunning) {
            long count = messageRepository.count();
            if (count != lastCount) {
                log.info("Количество записей в таблице: {}", count);
                lastCount = count;
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


