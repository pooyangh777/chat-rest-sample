package com.example.demo;

import asyncSdk.AsyncConfig;
import chatSdk.chat.Chat;
import chatSdk.chat.ChatListener;
import chatSdk.chat.listeners.MessageListener;
import chatSdk.dataTransferObject.ChatResponse;
import chatSdk.dataTransferObject.chat.ChatConfig;
import chatSdk.dataTransferObject.chat.ChatState;
import chatSdk.dataTransferObject.message.inPut.ResultNewMessage;
import chatSdk.dataTransferObject.system.outPut.ErrorOutPut;

import java.util.HashMap;

public class ChatManager implements ChatListener {
    Chat chat;
    public static String platformHost = "https://sandbox.pod.ir:8043";
    public static String socketAddress = "wss://msg.pod.ir/ws";
    public static String token = "90c2a567ec85476dad8defc550371ea9.XzIwMjM0";
    public static String ssoHost = "http://172.16.110.235";
    public static String fileServer = "https://core.pod.ir";
    public static String serverName = "chatlocal";
    public static String socketServerName = "chat-server";
    public static String queueServer = "192.168.112.23";
    public static String queuePort = "61616";
    public static String queueInput = "queue-in-chat-dotnet-local";
    public static String queueOutput = "queue-out-chat-dotnet-local";
    public static String queueUserName = "root";
    public static String queuePassword = "j]Bm0RU8gLhbPUG";
    public static Long chatId = 4101L;
    public HashMap<String, MessageListener> listenerMaps = new HashMap<>();

    public ChatManager() {
        init();
    }

    void init() {
        boolean isSocket = true;
        String serverName = isSocket ? socketServerName : this.serverName;
        AsyncConfig asyncConfig = AsyncConfig
                .builder()
                .isSocketProvider(isSocket)
                .socketAddress(socketAddress)
                .serverName(serverName)
                .queuePassword(queuePassword)
                .queueUserName(queueUserName)
                .queueInput(queueInput)
                .queueOutput(queueOutput)
                .queueServer(queueServer)
                .queuePort(queuePort)
                .isLoggable(true)
                .appId("PodChat")
                .build();
        ChatConfig chatConfig = ChatConfig.builder()
                .asyncConfig(asyncConfig)
                .severName(serverName)
                .token(token)
                .chatId(chatId)
                .fileServer(fileServer)
                .ssoHost(ssoHost)
                .platformHost(platformHost)
                .isLoggable(true)
                .build();
        try {
            chat = Chat.init(chatConfig, this);
            chat.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(String s, ErrorOutPut errorOutPut) {

    }

    @Override
    public void onChatState(ChatState chatState) {

    }

    @Override
    public void OnLogEvent(String s) {

    }

    @Override
    public void onNewMessage(String content, ChatResponse<ResultNewMessage> response) {
        ChatListener.super.onNewMessage(content, response);
        String uniqueId = response.getResult().getMessageVO().getUniqueId();
        MessageListener listener = listenerMaps.get(uniqueId);
        if (listener != null){
            listener.onNewMessage(content, response);
            listenerMaps.remove(uniqueId);
        }
    }
}
