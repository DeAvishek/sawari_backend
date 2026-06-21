package com.sawari.sawari.forRider.service;

import org.springframework.stereotype.Service;

@Service
public class RedisRideRequestSubscriber {

    public void onMessage(String message) {
        System.out.println("Ride request received: " + message);
    }
}
