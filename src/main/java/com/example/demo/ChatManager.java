package com.example.demo;

import asyncSdk.AsyncConfig;
import chatSdk.chat.Chat;
import chatSdk.chat.ChatListener;
import chatSdk.chat.listeners.MessageListener;
import chatSdk.dataTransferObject.ChatResponse;
import chatSdk.dataTransferObject.chat.ChatConfig;
import chatSdk.dataTransferObject.chat.ChatState;
import chatSdk.dataTransferObject.message.inPut.Message;
import chatSdk.dataTransferObject.system.outPut.ErrorOutPut;

import java.util.HashMap;

public class ChatManager implements ChatListener {
    private MyAppProperties appProperties;
    Chat chat;
    public HashMap<String, MessageListener> listenerMaps = new HashMap<>();


    public ChatManager(MyAppProperties appProperties) {
        this.appProperties = appProperties;
        init();
    }

    void init() {
        String serverName = appProperties.getIsSocket() ? appProperties.getSocketServerName() : appProperties.getServerName();
        AsyncConfig asyncConfig = AsyncConfig
                .builder()
                .isSocketProvider(appProperties.getIsSocket())
                .socketAddress(appProperties.getSocketAddress())
                .serverName(serverName)
                .queuePassword(appProperties.getQueuePassword())
                .queueUserName(appProperties.getQueueUserName())
                .queueInput(appProperties.getQueueInput())
                .queueOutput(appProperties.getQueueOutput())
                .queueServer(appProperties.getQueueServer())
                .queuePort(appProperties.getQueuePort())
                .isLoggable(appProperties.getIsLoggable())
                .appId(appProperties.getAppId())
                .build();
        ChatConfig chatConfig = ChatConfig.builder()
                .asyncConfig(asyncConfig)
                .severName(serverName)
                .token(appProperties.getToken())
                .chatId(appProperties.getChatId())
                .fileServer(appProperties.getFileServer())
                .ssoHost(appProperties.getSsoHost())
                .platformHost(appProperties.getPlatformHost())
                .isLoggable(appProperties.getIsLoggable())
                .maxReconnectCount(appProperties.getMaxReconnectCount())
                .reconnectInterval(appProperties.getReconnectInterval())
                .build();
        System.out.println("token is " + appProperties.getToken());
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
        if (chatState == ChatState.ChatReady) {
            System.out.println("Chat is ready.");
        } else {
            System.out.println("Chat state is " + chatState.toString());
        }
    }

    @Override
    public void OnLogEvent(String s) {

    }

    @Override
    public void onNewMessage(ChatResponse<Message> response) {
        ChatListener.super.onNewMessage(response);
        String uniqueId = response.getResult().getUniqueId();
        MessageListener listener = listenerMaps.get(uniqueId);
        if (listener != null) {
            listener.onNewMessage(response);
            listenerMaps.remove(uniqueId);
        }
    }
}
