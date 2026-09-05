package com.sawari.sawari.common.service;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.common.dto.OtpSession;
import com.sawari.sawari.forRider.entity.Rider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CommonRedisService {
    @Autowired
    public RedisTemplate<String, OtpSession> redisTemplateForOtpSession;

    @Autowired
    public RedisTemplate<String,String> redisTemplateForRefreshToken;

    @Autowired
    public RedisTemplate<String,Object> redisTemplateForDriver;

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long Expiry;

    private static final String DRIVER_LOCATION_KEY = "drivers:locations";

    public String setRefreshTokenForDriver(Driver driver){
        String token = UUID.randomUUID().toString();
        redisTemplateForRefreshToken.opsForValue().set(token,driver.getId().toString(),Expiry, TimeUnit.MILLISECONDS);
        log.info("Refresh token has been set for driver{}",token);
        return token;
    }
    public String setRefreshTokenForRider(Rider rider){
        String token = UUID.randomUUID().toString();
        redisTemplateForRefreshToken.opsForValue().set(token,rider.getId().toString(),Expiry, TimeUnit.MILLISECONDS);
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

    //-->for storing otp session in redis with ttl
    public void AddOtpSession(OtpSession otpSession){
        String key = "phone:"+otpSession.getPhoneNumber();
        redisTemplateForOtpSession.opsForValue().set(key,otpSession,5, TimeUnit.MINUTES);
        log.info("Otp session has been successfully added to cache with ttl 5 😍");
    }

    public boolean checkOtpInRedis(String phoneNumber,String Otp){
        String key = "phone:"+phoneNumber;
        try{
            OtpSession isExisted =  redisTemplateForOtpSession.opsForValue().get(key);
            if(isExisted!=null){
                log.info("***user verified in redis **** at"+LocalDateTime.now());
                redisTemplateForOtpSession.delete(key);
                log.info("***otp session of user delted from redis"+LocalDateTime.now());
                return isExisted.getPhoneNumber().equals(phoneNumber)&&isExisted.getOtp().equals(Otp);
            }
            log.warn("***otp expires***");
            return false;
        }catch(Exception e){
            return false;
        }
    }
    //--->end
    //find nearby drivers
    public List<String>findNearbyDrivers(double longitude,double latitude,int km){
        Point p= new Point(longitude,latitude);
        Circle circle = new Circle(
                p,
                new Distance(km, Metrics.KILOMETERS)
        );
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results =
                redisTemplateForDriver.opsForGeo().radius(
                        DRIVER_LOCATION_KEY,
                        circle
                );
        List<String> driverIds = new ArrayList<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> result : results) {
            Object driverId = result.getContent().getName();
            driverIds.add(String.valueOf(driverId));
        }
        return driverIds;
    }
}
