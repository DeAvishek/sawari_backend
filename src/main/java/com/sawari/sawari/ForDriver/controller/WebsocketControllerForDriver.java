package com.sawari.sawari.ForDriver.controller;
import com.sawari.sawari.ForDriver.service.redis.RedisServiceForDriver;
import com.sawari.sawari.common.dto.LocationWithDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebsocketControllerForDriver {

    @Autowired
    private RedisServiceForDriver  redisServiceForDriver;
    @MessageMapping("/sendLocation")
    @SendTo("/topic/locationStatus")
    public void SendDriverLocation(LocationWithDriver location) {
        System.out.println("SendDriverLocation..." +location.getLatitude()+","+location.getLongitude() + ","+location.getUserId());
        redisServiceForDriver.AddOnlineDriver(location.getUserId(),location.getLongitude(),location.getLatitude());
    }
}
