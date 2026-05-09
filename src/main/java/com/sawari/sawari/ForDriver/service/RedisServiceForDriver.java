package com.sawari.sawari.ForDriver.service;

import com.sawari.sawari.ForDriver.entity.Driver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class RedisServiceForDriver {
    @Autowired
    public RedisTemplate<String,Object> redisTemplateForOnlineDriver;

    public void AddOnlineDriver(Driver driver){
        String Key = "Active-driver:"+Integer.toString(driver.getId());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"driver",Integer.toString(driver.getId()));
//        redisTemplateForOnlineDriver.opsForHash().put(Key,"latitude",latitude);
//        redisTemplateForOnlineDriver.opsForHash().put(Key,"longitude",longitude);
        redisTemplateForOnlineDriver.opsForHash().put(Key,"phoneNo",driver.getPhoneNumber());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"isOnline",driver.getIsOnline());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"isOnRide",driver.getIsOnRide());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"updatedAt", LocalDateTime.now().toString());
        log.info("Successfully Added online Driver😍");
    }
}
