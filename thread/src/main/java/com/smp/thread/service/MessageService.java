package com.smp.thread.service;

import com.smp.thread.exception.CountMessageException;
import com.smp.thread.exception.InsertMessageException;
import com.smp.thread.exception.MessageServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Service
public class MessageService {

    @Autowired
    private InsertMessageService insertMessageService;

    @Autowired
    private CountMessageService countMessageService;

    private ExecutorService executorService;

    public void startInsertMessages() {
        executorService = Executors.newFixedThreadPool(2);
        try {
            executorService.submit(() -> {
                try {
                    insertMessageService.start();
                } catch (RuntimeException e) {
                    throw new InsertMessageException("Ошибка при вставке сообщений: " + e.getMessage());
                }
            });
            executorService.submit(() -> {
                try {
                    countMessageService.start();
                } catch (RuntimeException e) {
                    throw new CountMessageException("Ошибка при подсчете сообщений: " + e.getMessage());
                }
            });
        } catch (RuntimeException e) {
            throw new MessageServiceException("Не удалось запустить потоки для вставки и подсчета сообщений.");
        }
    }

    public void stopInsertMessages() {
        try {
            insertMessageService.stop();
            countMessageService.stop();

            executorService.shutdownNow();

            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                throw new MessageServiceException("Потоки не завершились за отведенное время и были принудительно остановлены.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isRunning() {
        return insertMessageService.isRunning();
    }
}