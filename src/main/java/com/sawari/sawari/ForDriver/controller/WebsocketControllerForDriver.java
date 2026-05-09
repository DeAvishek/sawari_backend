package com.sawari.sawari.ForDriver.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketControllerForDriver {
    @MessageMapping("/sendLocation")
    @SendTo("/topic/locationStatus")
    public void SendDriverLocation(String lat, String lon){
        System.out.println("SendDriverLocation" +lat+","+lon);
    }
}
