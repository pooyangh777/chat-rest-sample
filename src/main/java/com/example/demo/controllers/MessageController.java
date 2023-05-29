package com.example.demo.controllers;

import chatSdk.chat.listeners.MessageListener;
import chatSdk.dataTransferObject.ChatResponse;
import chatSdk.dataTransferObject.message.inPut.Message;
import chatSdk.dataTransferObject.message.outPut.SendMessageRequest;
import com.example.demo.Application;
import com.example.demo.controllers.dto.MessageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/message")
public class MessageController {
    @GetMapping(value = "/send")
    @Async
    public CompletableFuture<Message> send(@RequestParam(value = "message") String message, @RequestParam(value = "threadId") Long threadId) {
        return sendMessage(message, threadId);
    }

    @PostMapping("/send")
    @Async
    public CompletableFuture<Message> send(@RequestBody MessageRequest request) {
        return sendMessage(request.getMessage(), request.getThreadId());
    }

    private CompletableFuture<Message> sendMessage(String message, Long threadId){
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        SendMessageRequest req = new SendMessageRequest.Builder()
                .setMessage(message)
                .setThreadId(threadId)
                .build();
        String uniqueId = Application.chatManager.chat.sendTextMessage(req);
        Executors.newCachedThreadPool().submit(() -> {
            Application.chatManager.listenerMaps.put(uniqueId, new MessageListener() {
                @Override
                public void onNewMessage(ChatResponse<Message> response) {
                    completableFuture.complete(response.getResult());
                }
            });
        });
        return completableFuture;
    }
}
