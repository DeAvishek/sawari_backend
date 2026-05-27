package com.sawari.sawari.ForDriver.controller;
import com.sawari.sawari.dto.Location;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketControllerForDriver {
    @MessageMapping("/sendLocation")
    @SendTo("/topic/locationStatus")
    public void SendDriverLocation(Location location) {
        System.out.println("SendDriverLocation" +location.getLatitude()+","+location.getLongitude());
    }
}
