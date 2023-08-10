package com.regalaxy.phonesin.module.controller;

import com.regalaxy.phonesin.module.model.SignalDto;
import com.regalaxy.phonesin.module.model.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
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
    public void handleSignal(@RequestBody SignalDto signalDto) {
        String roomId = signalDto.getRoomId();
        Set<String> members = roomService.getRoomMember(roomId);

        if (members != null) {
            for (String member : members) {
                messagingTemplate.convertAndSendToUser(member, "/queue/signal", signalDto.getContent());
            }
        }
    }

    @GetMapping("/makeRoom/{roomId}/{session}")
    public ResponseEntity<?> makeRoom(@PathVariable("roomId") String roomId, @PathVariable("session") String session){
        roomService.createRoom(roomId, session);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/joinRoom/{roomId}/{session}")
    public ResponseEntity<?> joinRoom(@PathVariable("roomId") String roomId, @PathVariable("session") String session){
        roomService.joinRoom(roomId, session);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/leaveRoom/{roomId}/{session}")
    public ResponseEntity<?> leaveRoom(@PathVariable("roomId") String roomId, @PathVariable("session") String session){
        roomService.leaveRoom(roomId, session);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
