package com.sawari.sawari.controller;

import com.sawari.sawari.entity.TripRecord;
import com.sawari.sawari.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;
    @PostMapping("/create_trip_for/{RiderId}")
    public void CreateRecord(@PathVariable Integer RiderId, @RequestBody TripRecord tripRecord){
        tripService.SaveTrip(RiderId, tripRecord);
    }
}
