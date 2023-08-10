package com.regalaxy.phonesin.module.controller;

import com.regalaxy.phonesin.module.model.SignalDto;
import com.regalaxy.phonesin.module.model.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@Controller
@CrossOrigin("*")
public class SignalingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private RoomService roomService;

    public SignalingController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }

    @MessageMapping("/signal")
    public void handleSignal(SignalDto signalMessage) {
        String roomId = signalMessage.getRoomId();
        Set<String> members = roomService.getRoom(roomId);

        if (members != null) {
            for (String member : members) {
                messagingTemplate.convertAndSendToUser(member, "/queue/signal", signalMessage);
            }
        }
    }
}
