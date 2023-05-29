package com.example.demo.controllers;

import com.example.demo.Application;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection")
public class ConnectionController {
    @GetMapping(value = "/chat/status")
    public String chatStatus() {
        return Application.chatManager.chat.getState().toString();
    }

    @GetMapping(value = "/async/status")
    public String asyncStatus() {
        return Application.chatManager.chat.getAsyncState().toString();
    }

    @GetMapping("/reconnect")
    public void send() {
        Application.chatManager.chat.forceReconnect();
    }
}
