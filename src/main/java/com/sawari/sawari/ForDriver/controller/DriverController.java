package com.sawari.sawari.ForDriver.controller;
import com.sawari.sawari.ForDriver.service.general.DriverService;
import com.sawari.sawari.ForDriver.service.redis.RedisServiceForDriver;
import com.sawari.sawari.common.dto.IsReadyToAcceptRide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/Driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private RedisServiceForDriver redisServiceForDriver;

    @GetMapping("/summary/{phoneNumber}")
    public ResponseEntity<?>getTodaySummary(@PathVariable String phoneNumber){
        try{
            return new ResponseEntity<>(driverService.summaryService(phoneNumber), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/update_status")
    public ResponseEntity<?> updateStatus(@RequestBody IsReadyToAcceptRide isReadyToAcceptRide){
        return new ResponseEntity<>(redisServiceForDriver.UpdateReadyStatusOfDriver(isReadyToAcceptRide), HttpStatus.OK);
    }
    @GetMapping("/recent_trips/{phoneNumber}")
    public ResponseEntity<?> getRecentTrips(@PathVariable String phoneNumber){
        //dummy response to check frontend actually working
        HashMap<String,Object> map = new HashMap<>();
        map.put("source","xxx");
        map.put("destination","yyy");
        map.put("fare",1200);
        map.put("distance",12);
        map.put("duration",190);
        map.put("tripStatus","completed");
        map.put("completedAt", LocalDateTime.now());
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list.add(map);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
//    public
}
