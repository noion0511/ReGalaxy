package com.regalaxy.phonesin.module.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class StreamingWebSocketHandler implements WebSocketHandler {

    private ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 클라이언트가 연결되었을 때 수행할 작업
        // 예: 세션 관리, 초기 데이터 전송 등
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //클라이언트가 서버로 데이터를 보낼 때 마다 실행
        if (message instanceof BinaryMessage) {
            // Handle binary streaming data
            byte[] data = ((BinaryMessage) message).getPayload().array();
            // Process and forward the data as needed
            forwardDataToClients(data);
        } else if (message instanceof TextMessage) {
            // Handle text messages if needed
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //전송 오류 처리
        //예 : 연결 종료, 연결 재시도 등
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        //클라이언트가 연결을 끊었을 때
        sessions.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        // WebSocket 세션이 부분 메시지를 지원하는지를 판별하기 위해 사용
        return false;//true로 하면 부분 메세지 가능하도록 설정하는거라네...... 맞나???
    }

    private void forwardDataToClients(byte[] data) throws IOException {
        //클라이언트로 메세지 보내기
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(new BinaryMessage(data));
            }
        }
    }
}