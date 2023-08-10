package com.regalaxy.phonesin.module.controller;

import com.regalaxy.phonesin.module.model.SignalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLOutput;
import java.util.Set;

@Controller
@CrossOrigin("*")
public class SignalingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/signal")
    public void handleSignal(@RequestBody SignalDto signalDto) {
        System.out.println(signalDto.getContent().toString());
        messagingTemplate.convertAndSend("/topic/"+signalDto.getRoomId(), signalDto.getContent());
    }
}
