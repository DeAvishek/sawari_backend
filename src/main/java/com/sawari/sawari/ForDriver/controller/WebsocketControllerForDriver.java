package com.sawari.sawari.ForDriver.controller;
import com.sawari.sawari.pojo.RedisTripSession;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketControllerForDriver {
    @MessageMapping("/sendLocation")
    @SendTo("/topic/locationStatus")
    public void SendDriverLocation(RedisTripSession redisTripSession){
        System.out.println("SendDriverLocation" +redisTripSession.getDriverId()+","+redisTripSession.getRiderId());
    }
}
