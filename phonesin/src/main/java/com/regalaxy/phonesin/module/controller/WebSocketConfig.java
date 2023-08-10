package com.regalaxy.phonesin.module.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final StreamingWebSocketHandler streamingWebSocketHandler;

    public WebSocketConfig(StreamingWebSocketHandler streamingWebSocketHandler) {
        this.streamingWebSocketHandler = streamingWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(streamingWebSocketHandler, "/websocket-endpoint")
                .setAllowedOrigins("*"); // You can restrict origins if needed
    }
}
