package com.sawari.sawari.ForDriver.service.redis;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.helper.RedisKey;
import com.sawari.sawari.common.dto.IsReadyToAcceptRide;
import com.sawari.sawari.common.dto.OtpSession;
import com.sawari.sawari.common.dto.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
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
    @Qualifier("driverGeoRedisTemplate")
    public RedisTemplate<String,String> redisTemplateForDriver;

    @Autowired
    public RedisTemplate<String, OtpSession> redisTemplateForOtpSession;

//    @Autowired
//    @Qualifier("redisTemplate3ForAutocomplete")
//    public RedisTemplate<String,String> redisTemplateForRefreshToken;

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long Expiry;
    private static final String driverLocationKey = "Active:driver";
    public void AddOnlineDriver(String driverId,double longitude,double latitude){
        redisTemplateForDriver.opsForGeo().add(
                driverLocationKey,
                new Point(longitude,latitude),
                driverId
        );
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
    public String UpdateReadyStatusOfDriver(IsReadyToAcceptRide  isReadyToAcceptRide){
        String Key = RedisKey.Active_Driver.name()+":"+isReadyToAcceptRide.getDriverId();
        redisTemplateForDriver.opsForHash().put(Key,"isReady",isReadyToAcceptRide.getValue());
        redisTemplateForDriver.opsForHash().put(Key,"updatedAt",LocalDateTime.now().toString());
        log.info("Driver id with "+isReadyToAcceptRide.getDriverId() +"Status is updated to "+
                (boolean)redisTemplateForDriver.opsForHash().get(Key,"isReady"));
        return "status updated";
    }

}
