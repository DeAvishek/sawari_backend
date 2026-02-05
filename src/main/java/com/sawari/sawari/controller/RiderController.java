package com.sawari.sawari.controller;

import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Rider")
public class RiderController {

    @Autowired
    private RiderService riderService;
    @PostMapping("/create_user")
    public void createRider(@RequestBody Rider rider){
        riderService.CreateRider(rider);
    }
}
