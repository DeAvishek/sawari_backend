package com.sawari.sawari.ForDriver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisServiceForDriver {
    @Autowired
    public RedisTemplate<String,Object> redisTemplateForOnlineDriver;

    public void AddOnlineDriver(Integer driverId,String latitude,String longitude,String phoneNo,boolean isOnline,boolean isOnRide){
        String Key = "Active-driver:"+driverId;
        redisTemplateForOnlineDriver.opsForHash().put(Key,"driver",driverId);
        redisTemplateForOnlineDriver.opsForHash().put(Key,"latitude",latitude);
        redisTemplateForOnlineDriver.opsForHash().put(Key,"longitude",longitude);
        redisTemplateForOnlineDriver.opsForHash().put(Key,"phoneNo",phoneNo);
        redisTemplateForOnlineDriver.opsForHash().put(Key,"isOnline",isOnline);
        redisTemplateForOnlineDriver.opsForHash().put(Key,"isOnRide",isOnRide);
        log.info("Successfully Added online rider😍");
    }
}
