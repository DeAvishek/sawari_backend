package com.sawari.sawari.forRider.controller;

import com.sawari.sawari.forRider.entity.TripRecord;
import com.sawari.sawari.forRider.service.redis.RedisService;
import com.sawari.sawari.forRider.service.genral.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trip")
@Slf4j
public class TripController {
    @Autowired
    private TripService tripService;
    @Autowired
    private RedisService redisService;
    @PostMapping("/create_trip_for/{RiderId}")
    public ResponseEntity<?> createRide(@PathVariable Integer RiderId, @RequestBody TripRecord tripRecord){
        try {
            //Flows::persist the ride in MySql--->create a redis session of ride---->return the response
             TripRecord response= tripService.saveTrip(RiderId, tripRecord); //persist in db
             redisService.createRideSession(response.getRider().getId(),response.getId(),response.getSource(),response.getDestination()); //create a session in Redis
             Map<String,String> result=new HashMap<>();
             result.put("message","Ride created successfully 😍");
             return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
