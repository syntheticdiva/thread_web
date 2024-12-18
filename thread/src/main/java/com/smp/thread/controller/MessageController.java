package com.smp.thread.controller;

import com.smp.thread.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/start")
    public ResponseEntity<String> startInsert() {
        messageService.startInsertMessages();
        return ResponseEntity.ok("Insert started");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopInsert() {
        messageService.stopInsertMessages();
        return ResponseEntity.ok("Insert stopped");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        boolean isRunning = messageService.isRunning();
        return ResponseEntity.ok(isRunning ? "Insert is running" : "Insert is stopped");
    }
}
