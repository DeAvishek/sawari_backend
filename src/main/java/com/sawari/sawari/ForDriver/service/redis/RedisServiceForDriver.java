package com.sawari.sawari.ForDriver.service.redis;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.common.dto.OtpSession;
import com.sawari.sawari.common.dto.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceForDriver {
    @Autowired
    public RedisTemplate<String,Object> redisTemplateForDriver;

    @Autowired
    public RedisTemplate<String, OtpSession> redisTemplateForOtpSession;

    @Autowired
    public RedisTemplate<String,String> redisTemplateForRefreshToken;

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long Expiry;

    public void AddOnlineDriver(Driver driver){
        String Key = "Active-driver:"+Integer.toString(driver.getId());
        redisTemplateForDriver.opsForHash().put(Key,"driver",Integer.toString(driver.getId()));
        redisTemplateForDriver.opsForHash().put(Key,"latitude","");
        redisTemplateForDriver.opsForHash().put(Key,"longitude","");
        redisTemplateForDriver.opsForHash().put(Key,"phoneNo",driver.getPhoneNumber());
        redisTemplateForDriver.opsForHash().put(Key,"isReady",false);
        redisTemplateForDriver.opsForHash().put(Key,"isOnRide",false);
        redisTemplateForDriver.opsForHash().put(Key,"updatedAt", LocalDateTime.now().toString());
        log.info("Successfully Added online Driver😍");
    }
    //--->end
    public HashMap<String, Integer> getTodaySummary(String phoneNumber){
        String Key = "summary:"+phoneNumber;
        boolean isNew = Boolean.FALSE.equals(redisTemplateForDriver.hasKey(Key));
        if(isNew){
            /// create a dummy summary
        }
        redisTemplateForDriver.opsForHash().put(Key,"earnings",1200);
        redisTemplateForDriver.opsForHash().put(Key,"rides",20);
        redisTemplateForDriver.opsForHash().put(Key,"rating",4);
        redisTemplateForDriver.opsForHash().put(Key,"onlineTime",340);
        if(isNew){
            log.info("first time creating summary at "+LocalDateTime.now().toString());
            redisTemplateForDriver.expire(Key,1,TimeUnit.DAYS);
        }
        HashMap<String,Integer>map=new HashMap<>();
        map.put("earnings",(int) redisTemplateForDriver.opsForHash().get(Key,"earnings"));
        map.put("rides",(int) redisTemplateForDriver.opsForHash().get(Key,"rides"));
        map.put("rating",(int) redisTemplateForDriver.opsForHash().get(Key,"rating"));
        map.put("onlineTime",(int) redisTemplateForDriver.opsForHash().get(Key,"onlineTime"));
        return map;

    }

}
