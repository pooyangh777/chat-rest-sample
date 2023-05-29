package com.example.demo.controllers;

import chatSdk.chat.listeners.ThreadListener;
import chatSdk.dataTransferObject.ChatResponse;
import chatSdk.dataTransferObject.thread.inPut.Conversation;
import chatSdk.dataTransferObject.thread.outPut.GetThreadRequest;
import com.example.demo.Application;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/thread")
public class ThreadController {
    @GetMapping()
    @Async
    public CompletableFuture<List<Conversation>> send(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                           @RequestParam(value = "count", defaultValue = "25") int count) {
        return getThreads(offset, count);
    }

    private CompletableFuture<List<Conversation>> getThreads(int offset, int count){
        CompletableFuture<List<Conversation>> completableFuture = new CompletableFuture<>();
        GetThreadRequest req = new GetThreadRequest.Builder()
                .setOffset(offset)
                .setCount(count)
                .build();
        String uniqueId = Application.chatManager.chat.getThreads(req);
        Executors.newCachedThreadPool().submit(() -> {
            Application.chatManager.listenerMaps.put(uniqueId, new ThreadListener() {
                @Override
                public void onGetThread(ChatResponse<Conversation[]> response){
                    completableFuture.complete(Arrays.asList(response.getResult()));
                }
            });
        });
        return completableFuture;
    }
}
