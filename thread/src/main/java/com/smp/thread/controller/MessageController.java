package com.smp.thread.controller;

import com.smp.thread.service.MessageService;
import com.smp.thread.service.ThreadStateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ThreadStateService threadStateService;

    @PostMapping("/start")
    public ResponseEntity<String> startInsert(HttpSession session) {
        messageService.startInsertMessages();
        threadStateService.setRunning(true);
        session.setAttribute("isRunning", true);
        return ResponseEntity.ok("Insert started");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopInsert(HttpSession session) {
        messageService.stopInsertMessages();
        threadStateService.setRunning(false);
        session.setAttribute("isRunning", false);
        return ResponseEntity.ok("Insert stopped");
    }

    @GetMapping("/state")
    public ResponseEntity<Map<String, Boolean>> getState(HttpSession session) {
        boolean isRunning = threadStateService.isRunning();

        session.setAttribute("isRunning", isRunning);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isRunning", isRunning);
        return ResponseEntity.ok(response);
    }
}