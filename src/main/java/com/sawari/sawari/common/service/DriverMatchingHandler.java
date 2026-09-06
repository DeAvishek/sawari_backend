//Chain of responsibility
package com.sawari.sawari.common.service;
import java.util.Collections;
import java.util.List;

public class DriverMatchingHandler {
    private CommonRedisService commonRedisService;
    private int radius;
    private DriverMatchingHandler nextHandler;
    public DriverMatchingHandler(CommonRedisService commonRedisService, int radius) {
        this.commonRedisService = commonRedisService;
        this.radius = radius;
    }
    public void setNext(DriverMatchingHandler driverMatchingHandler){
        this.nextHandler = driverMatchingHandler;
    }
    public List<String>findDrivers(double longitude, double latitude){
        List<String>drivers=commonRedisService.findNearbyDrivers(longitude,latitude,radius);
        if(!drivers.isEmpty()){
            return drivers;
        }
        if(nextHandler!=null){
            drivers=nextHandler.findDrivers(longitude,latitude);
            return drivers;
        }
        return Collections.emptyList();
    }

}
