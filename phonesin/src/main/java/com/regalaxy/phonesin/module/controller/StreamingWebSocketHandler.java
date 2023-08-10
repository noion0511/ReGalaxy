package com.regalaxy.phonesin.module.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.sun.mail.util.ASCIIUtility.getBytes;

@Component
@CrossOrigin
public class StreamingWebSocketHandler implements WebSocketHandler {

    private ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String, String> rooms = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        // 클라이언트가 연결되었을 때 수행할 작업
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //클라이언트가 서버로 데이터를 보낼 때 마다 실행
        if (message instanceof BinaryMessage) {//메세지가 바이너리 메세지인지 확인
            byte[] data = ((BinaryMessage) message).getPayload().array();//이진수로 변환
            forwardDataToClients(data, "102", null);//클라이언트로 보내기
        } else if (message instanceof TextMessage) {//메세지가 텍스트 메세지면
            String[] msg = ((TextMessage) message).getPayload().split(":");
            byte[] data;
            switch(msg[0]){
                case("makeRoom")://방 만들기
                    rooms.put(session.getId(), "102");//방 번호 생성, 입장
                    System.out.println(rooms.get(session.getId()) + session.getId());
                    data = "102".getBytes();//방 번호 클라이언트에게 보내주기
                    forwardDataToClients(data, "102", session.getId());//나에게만
                    break;
                case("roomnum")://방에 들어오기
                    rooms.put(session.getId(), "102");//입장
                    System.out.println(rooms.get(session.getId()) + session.getId());
                    data = "방에 들어왔습니다.".getBytes();//들어왔다고 보내주기
                    forwardDataToClients(data, "102", session.getId());//나에게만
                    break;
                case("text")://문자 보내기
                    data = msg[1].getBytes();//데이터 바이트화
                    System.out.println(rooms.get(session.getId()) + session.getId());
                    forwardDataToClients(data, "102", null);//방에만 보내기
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //전송 오류 처리
        //오류가 나면 일단 연결 종료
        if(session.isOpen()){
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        //클라이언트가 연결을 끊었을 때
        sessions.remove(session.getId());
        rooms.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        // WebSocket 세션이 부분 메시지를 지원하는지를 판별하기 위해 사용
        return true;//true로 하면 부분 메세지 가능하도록 설정하는거라네...... 맞나???
    }

    private void forwardDataToClients(byte[] data, String roomnum, String me) throws IOException {
        //클라이언트로 메세지 보내기
        for (String sessionId : sessions.keySet()) {
            if(me!=null && sessionId.equals(me)) {//나에게만 보내는거
                if (sessions.get(sessionId).isOpen()) {
                    sessions.get(sessionId).sendMessage(new BinaryMessage(data));
                }
            }else if(rooms!=null && rooms.get(sessionId).equals(roomnum)){//같은 방에만 보내는 거
                if (sessions.get(sessionId).isOpen()) {
                    sessions.get(sessionId).sendMessage(new BinaryMessage(data));
                }
            }
        }
    }
}