package com.sawari.sawari.ForDriver.controller;

import com.sawari.sawari.ForDriver.service.general.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/summary/{phoneNumber}")
    public ResponseEntity<?>getTodaySummary(@PathVariable String phoneNumber){
        try{
            return new ResponseEntity<>(driverService.summaryService(phoneNumber), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
