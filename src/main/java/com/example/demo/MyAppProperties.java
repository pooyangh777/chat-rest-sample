package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myapp")
public class MyAppProperties {
    private String platformHost;
    private String socketAddress;
    private String token;
    private String ssoHost;
    private String fileServer;
    private String serverName;
    private String socketServerName;
    private String queueServer;
    private String queuePort;
    private String queueInput;
    private String queueOutput;
    private String queueUserName;
    private String queuePassword;
    private Boolean isSocket = false;
    private Boolean isLoggable = false;
    private String appId = "PodChat";
    private Long chatId;

    public String getPlatformHost() {
        return platformHost;
    }

    public void setPlatformHost(String platformHost) {
        this.platformHost = platformHost;
    }

    public String getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(String socketAddress) {
        this.socketAddress = socketAddress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSsoHost() {
        return ssoHost;
    }

    public void setSsoHost(String ssoHost) {
        this.ssoHost = ssoHost;
    }

    public String getFileServer() {
        return fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getSocketServerName() {
        return socketServerName;
    }

    public void setSocketServerName(String socketServerName) {
        this.socketServerName = socketServerName;
    }

    public String getQueueServer() {
        return queueServer;
    }

    public void setQueueServer(String queueServer) {
        this.queueServer = queueServer;
    }

    public String getQueuePort() {
        return queuePort;
    }

    public void setQueuePort(String queuePort) {
        this.queuePort = queuePort;
    }

    public String getQueueInput() {
        return queueInput;
    }

    public void setQueueInput(String queueInput) {
        this.queueInput = queueInput;
    }

    public String getQueueOutput() {
        return queueOutput;
    }

    public void setQueueOutput(String queueOutput) {
        this.queueOutput = queueOutput;
    }

    public String getQueueUserName() {
        return queueUserName;
    }

    public void setQueueUserName(String queueUserName) {
        this.queueUserName = queueUserName;
    }

    public String getQueuePassword() {
        return queuePassword;
    }

    public void setQueuePassword(String queuePassword) {
        this.queuePassword = queuePassword;
    }

    public Boolean getIsSocket() {
        return isSocket;
    }

    public void setIsSocket(Boolean socket) {
        isSocket = socket;
    }

    public Boolean getSocket() {
        return isSocket;
    }

    public Boolean getIsLoggable() {
        return isLoggable;
    }

    public void setIsLoggable(Boolean loggable) {
        isLoggable = loggable;
    }

    public Boolean getLoggable() {
        return isLoggable;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
