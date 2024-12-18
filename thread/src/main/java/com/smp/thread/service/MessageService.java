package com.smp.thread.service;

import com.smp.thread.exception.CountMessageException;
import com.smp.thread.exception.InsertMessageException;
import com.smp.thread.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class MessageService {

    @Autowired
    private InsertMessageService insertMessageService;

    @Autowired
    private CountMessageService countMessageService;
    @Autowired
    private MessageRepository messageRepository;

    private ExecutorService executorService1;
    private ExecutorService executorService2;

    public void startInsertMessages(int threadNumber) {
        if (threadNumber == 1) {
            executorService1 = Executors.newFixedThreadPool(2);
            executorService1.submit(() -> {
                try {
                    insertMessageService.start(threadNumber);
                } catch (RuntimeException e) {
                    throw new InsertMessageException("Ошибка при вставке сообщений в потоке 1: " + e.getMessage());
                }
            });

            executorService1.submit(() -> {
                try {
                    countMessageService.start();
                } catch (RuntimeException e) {
                    throw new CountMessageException("Ошибка при подсчете сообщений: " + e.getMessage());
                }
            });
        } else if (threadNumber == 2) {
            executorService2 = Executors.newSingleThreadExecutor();
            executorService2.submit(() -> {
                try {
                    insertMessageService.start(threadNumber);
                } catch (RuntimeException e) {
                    throw new InsertMessageException("Ошибка при вставке сообщений в потоке 2: " + e.getMessage());
                }
            });
        }
    }
    public void stopInsertMessages(int threadNumber) {
        try {
            insertMessageService.stop(threadNumber);

            if (threadNumber == 1) {
                countMessageService.stop();
                executorService1.shutdownNow();
                if (!executorService1.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService1.shutdownNow();
                }
            } else if (threadNumber == 2) {
                executorService2.shutdownNow();
                if (!executorService2.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService2.shutdownNow();
                }

                long count = messageRepository.count();
                log.debug("Вставлено записей во втором потоке: {}", count);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isRunning(int threadNumber) {
        if (threadNumber == 1) {
            return insertMessageService.isRunning(threadNumber);
        }
        return insertMessageService.isRunning(threadNumber);
    }
}
