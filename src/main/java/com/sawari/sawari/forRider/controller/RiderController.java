package com.sawari.sawari.forRider.controller;

import com.sawari.sawari.forRider.entity.Rider;
import com.sawari.sawari.common.dto.PhoneNumber;
import com.sawari.sawari.forRider.service.genral.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
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

    //login service to verify existed user by PhoneNumber and otp
    @PostMapping("/login")
    public ResponseEntity<?> LoginValidRider(@RequestBody PhoneNumber phNo){
        try{
            Rider existedRider = riderService.loginService(phNo);
            return new ResponseEntity<>(existedRider,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
