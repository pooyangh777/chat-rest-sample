package com.example.demo;

import chatSdk.chat.listeners.MessageListener;
import chatSdk.dataTransferObject.ChatResponse;
import chatSdk.dataTransferObject.message.inPut.MessageVO;
import chatSdk.dataTransferObject.message.inPut.ResultNewMessage;
import chatSdk.dataTransferObject.message.outPut.SendMessageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/message")
public class MessageController {
    @GetMapping(value = "/send")
    @Async
    public CompletableFuture<MessageVO> send(@RequestParam(value = "message") String message, @RequestParam(value = "threadId") Long threadId) {
        return sendMessage(message, threadId);
    }

    @PostMapping("/send")
    @Async
    public CompletableFuture<MessageVO> send(@RequestBody MessageRequest request) {
        return sendMessage(request.getMessage(), request.getThreadId());
    }

    private CompletableFuture<MessageVO> sendMessage(String message, Long threadId){
        CompletableFuture<MessageVO> completableFuture = new CompletableFuture<>();
        SendMessageRequest req = new SendMessageRequest.Builder()
                .setMessage(message)
                .setThreadId(threadId)
                .setSubjectId(threadId)
                .build();
        String uniqueId = DemoApplication.chatManager.chat.sendTextMessage2(req);
        Executors.newCachedThreadPool().submit(() -> {
            DemoApplication.chatManager.listenerMaps.put(uniqueId, new MessageListener() {
                @Override
                public void onNewMessage(String content, ChatResponse<ResultNewMessage> response) {
                    completableFuture.complete(response.getResult().getMessageVO());
                }
            });
        });
        return completableFuture;
    }
}
