package com.sawari.sawari.controller;

import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Rider")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/create_user")
    public ResponseEntity<?> createRider(@RequestBody Rider rider){
        try{
            Rider savedRider = riderService.CreateRider(rider);
            return new ResponseEntity<>(savedRider,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/say_hello")
    public ResponseEntity<?> sayHelloToPushpa(){
        try {
            return new ResponseEntity<>("Hello Pushpa", HttpStatus.OK);
        }catch (Exception e){
//            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
