package com.sawari.sawari.forRider.controller;

import com.sawari.sawari.common.dto.RedisTripSession;
import com.sawari.sawari.common.service.CommonRedisService;
import com.sawari.sawari.forRider.entity.TripRecord;
import com.sawari.sawari.forRider.service.genral.TripService;
import com.sawari.sawari.forRider.service.redis.RedisServiceForRider;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebsocketController {
    private final TripService tripService;
    private final RedisServiceForRider redisServiceForRider;
    private final CommonRedisService  commonRedisService;
    public WebsocketController(TripService tripService, RedisServiceForRider redisServiceForRider,CommonRedisService commonRedisService) {
        this.tripService = tripService;
        this.redisServiceForRider = redisServiceForRider;
        this.commonRedisService = commonRedisService;
    }
    @MessageMapping("/sendTrip")
    @SendTo("/topic/tripStatus")
    public RedisTripSession sendTripMessage(RedisTripSession redisTripSession)
    {
        System.out.println("got it...:)" + redisTripSession);
        TripRecord trip = tripService.saveTrip(redisTripSession);
        redisServiceForRider.createRideSession(trip.getRider().getId(),trip.getId(),trip.getSource(),trip.getDestination());
        List<String>nearbyDrivers = commonRedisService.findNearbyDrivers(redisTripSession.getLongitude(),redisTripSession.getLatitude(),5);
        return redisTripSession;
    }
}
