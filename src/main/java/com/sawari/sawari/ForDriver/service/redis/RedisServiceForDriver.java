package com.sawari.sawari.ForDriver.service.redis;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.common.dto.OtpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceForDriver {
    @Autowired
    public RedisTemplate<String,Object> redisTemplateForOnlineDriver;

    @Autowired
    public RedisTemplate<String, OtpSession> redisTemplateForOtpSession;

    @Autowired
    public RedisTemplate<String,String> redisTemplateForRefreshToken;

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long Expiry;

    public void AddOnlineDriver(Driver driver){
        String Key = "Active-driver:"+Integer.toString(driver.getId());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"driver",Integer.toString(driver.getId()));
        redisTemplateForOnlineDriver.opsForHash().put(Key,"latitude","");
        redisTemplateForOnlineDriver.opsForHash().put(Key,"longitude","");
        redisTemplateForOnlineDriver.opsForHash().put(Key,"phoneNo",driver.getPhoneNumber());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"isOnline",driver.getIsOnline());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"isOnRide",driver.getIsOnRide());
        redisTemplateForOnlineDriver.opsForHash().put(Key,"updatedAt", LocalDateTime.now().toString());
        log.info("Successfully Added online Driver😍");
    }
    //--->end
}
