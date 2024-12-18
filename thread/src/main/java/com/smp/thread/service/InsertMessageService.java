package com.smp.thread.service;

import com.smp.thread.entity.MessageEntity;
import com.smp.thread.exception.MessageServiceException;
import com.smp.thread.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Service
public class InsertMessageService {

    @Autowired
    private MessageRepository messageRepository;

    private static final int BATCH_SIZE = 100;
    private static final long SLEEP_DURATION = 200;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public void insertMessages() {
        while (isRunning.get()) {
            List<MessageEntity> messages = createMessages();
            try {
                messageRepository.saveAll(messages);
            } catch (RuntimeException e) {
                throw new MessageServiceException("Ошибка при сохранении сообщений", e);
            }
            sleep();
        }
    }

    public void start() {
        isRunning.set(true);
        insertMessages();
    }

    public void stop() {
        isRunning.set(false);
    }

    public boolean isRunning() {
        return isRunning.get();
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

    private void sleep() {
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}