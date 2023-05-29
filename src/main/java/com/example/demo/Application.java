package com.example.demo;

import com.example.demo.configuration.MyAppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static ChatManager chatManager;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        MyAppProperties app = context.getBean(MyAppProperties.class);
        chatManager = new ChatManager(app);
    }
}
