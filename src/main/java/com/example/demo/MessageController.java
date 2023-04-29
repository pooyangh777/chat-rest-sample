package com.example.demo;

import chatSdk.chat.listeners.MessageListener;
import chatSdk.dataTransferObject.ChatResponse;
import chatSdk.dataTransferObject.message.inPut.MessageVO;
import chatSdk.dataTransferObject.message.inPut.ResultNewMessage;
import chatSdk.dataTransferObject.message.outPut.SendMessageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/message")
public class MessageController {
    @RequestMapping(value = "/send")
    @Async
    public CompletableFuture<MessageVO> sendMessage(@RequestParam(value = "message") String message, @RequestParam(value = "threadId") Long threadId) {
        CompletableFuture<MessageVO> completableFuture = new CompletableFuture<>();
        SendMessageRequest req = new SendMessageRequest.Builder()
                .setMessage(message)
                .setThreadId(threadId)
                .setMessageType(1)
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
