package com.sawari.sawari.forRider.controller;

import com.sawari.sawari.common.dto.RedisTripSession;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    @MessageMapping("/sendTrip")
    @SendTo("/topic/tripStatus")
    public RedisTripSession sendTripMessage(RedisTripSession redisTripSession)
    {
        System.out.println("got it..." + redisTripSession);
        return redisTripSession;
    }
}
