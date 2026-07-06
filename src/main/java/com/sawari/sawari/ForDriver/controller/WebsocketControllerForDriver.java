package com.sawari.sawari.ForDriver.controller;
import com.sawari.sawari.ForDriver.helper.RedisKey;
import com.sawari.sawari.common.dto.LocationWithDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebsocketControllerForDriver {

    @Autowired
    public RedisTemplate<String,Object> redisTemplateForDriver;
    @MessageMapping("/sendLocation")
    @SendTo("/topic/locationStatus")
    public void SendDriverLocation(LocationWithDriver location) {
        System.out.println("SendDriverLocation..." +location.getLatitude()+","+location.getLongitude() + ","+location.getUserId());
        String Key = RedisKey.Active_Driver.name()+":"+location.getUserId();
        System.out.println(Key); //to   do to remove
        redisTemplateForDriver.opsForHash().put(Key,"driverId",location.getUserId());
        redisTemplateForDriver.opsForHash().put(Key,"latitude",location.getLatitude());
        redisTemplateForDriver.opsForHash().put(Key,"longitude",location.getLongitude());
        redisTemplateForDriver.opsForHash().put(Key,"updatedAt", LocalDateTime.now().toString());
    }
}
