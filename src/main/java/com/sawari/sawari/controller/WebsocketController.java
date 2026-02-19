package com.sawari.sawari.controller;

import com.sawari.sawari.pojo.RedisTripSession;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    @MessageMapping("/sendTrip")
    @SendTo("/topic/tripStatus")
    public RedisTripSession sendTripMessage(RedisTripSession redisTripSession)
    {
        System.out.println("sendTripMessage");
        return redisTripSession;
    }
}
