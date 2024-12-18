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

    @PostMapping("/start1")
    public ResponseEntity<String> startInsert1() {
        messageService.startInsertMessages(1);
        return ResponseEntity.ok("Insert 1 started");
    }

    @PostMapping("/stop1")
    public ResponseEntity<String> stopInsert1() {
        messageService.stopInsertMessages(1);
        return ResponseEntity.ok("Insert 1 stopped");
    }

    @PostMapping("/start2")
    public ResponseEntity<String> startInsert2() {
        messageService.startInsertMessages(2);
        return ResponseEntity.ok("Insert 2 started");
    }

    @PostMapping("/stop2")
    public ResponseEntity<String> stopInsert2() {
        messageService.stopInsertMessages(2);
        return ResponseEntity.ok("Insert 2 stopped");
    }

    @GetMapping("/status1")
    public ResponseEntity<String> getStatus1() {
        boolean isRunning = messageService.isRunning(1);
        return ResponseEntity.ok(isRunning ? "Insert 1 is running" : "Insert 1 is stopped");
    }

    @GetMapping("/status2")
    public ResponseEntity<String> getStatus2() {
        boolean isRunning = messageService.isRunning(2);
        return ResponseEntity.ok(isRunning ? "Insert 2 is running" : "Insert 2 is stopped");
    }
}