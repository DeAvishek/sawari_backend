package com.sawari.sawari.ForDriver.service;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.dto.OtpSession;
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

    public String setRefreshToken(Driver driver){
        String token = UUID.randomUUID().toString();
        redisTemplateForRefreshToken.opsForValue().set(token,driver.getId().toString(),Expiry,TimeUnit.MILLISECONDS);
        log.info("Refresh token has been set to {}",token);
        return token;
    }
    public int verifyRefreshToken(String token){
        String driverId = redisTemplateForRefreshToken.opsForValue().get(token);
        if(driverId==null) return 0;
        log.info("Refresh token has been verified in {}",driverId);
        return Integer.parseInt(driverId);
    }

    public void deleteRefreshToken(String token){
        log.info("Refresh token has been deleted in {}",token);
        redisTemplateForRefreshToken.delete(token);
    }

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

    public void AddOtpSession(OtpSession otpSession){
        String key = "phone:"+otpSession.getPhoneNumber();
        redisTemplateForOtpSession.opsForValue().set(key,otpSession,5, TimeUnit.MINUTES);
        log.info("Otp session has been successfully added to cache with ttl 5 😍");
    }
    //verify for otp in redis
    public boolean checkOtpInRedis(String phoneNumber,String Otp){
        String key = "phone:"+phoneNumber;
        try{
            OtpSession isExisted =  redisTemplateForOtpSession.opsForValue().get(key);
            if(isExisted!=null){
                log.info("***user verified in redis **** at"+LocalDateTime.now());
                return isExisted.getPhoneNumber().equals(phoneNumber)&&isExisted.getOtp().equals(Otp);
            }
            log.warn("***otp expires***");
            return false;
        }catch(Exception e){
            return false;
        }
    }
}
