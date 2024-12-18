package com.smp.thread.service;

import com.smp.thread.entity.ThreadState;
import com.smp.thread.repository.ThreadStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadStateService {
    @Autowired
    private ThreadStateRepository threadStateRepository;

    public void setRunning(boolean isRunning) {
        ThreadState state = threadStateRepository.findById("MESSAGE_THREAD")
                .orElse(new ThreadState());
        state.setId("MESSAGE_THREAD");
        state.setRunning(isRunning);
        threadStateRepository.save(state);
    }

    public boolean isRunning() {
        return threadStateRepository.findById("MESSAGE_THREAD")
                .map(ThreadState::isRunning)
                .orElse(false);
    }
}