package com.sawari.sawari.ForDriver.service.general;
import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import com.sawari.sawari.ForDriver.service.redis.RedisServiceForDriver;
import com.sawari.sawari.common.dto.OtpPojo;
import com.sawari.sawari.common.service.CommonRedisService;
import com.sawari.sawari.forRider.service.twillio.OtpGeneratorAndSenderService;
import com.sawari.sawari.common.utiils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private OtpGeneratorAndSenderService otpGeneratorAndSenderService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisServiceForDriver redisServiceForDriver;

    @Autowired
    private CommonRedisService commonRedisService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplateForOnlineDriver;
    public String saveDriver(Driver requestBody){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        Driver savedDriver = driverRepository.save(requestBody);
        return savedDriver.getId()+"#"+
                savedDriver.getUserName()+"#"+
                jwtUtil.GenerateJwtToken(savedDriver.getUserName())+"#"+
                commonRedisService.setRefreshTokenForDriver(savedDriver);
                //return the bearer , refresh-token with username for  logg in user
    }
    public void updateinRedis(Driver existedDriver){
        redisServiceForDriver.AddOnlineDriver(existedDriver);
    }

    @Scheduled(fixedRate = 60000)
    public void cleanOfflineDriversFromRedis(){
        Set<String>redisKeys = redisTemplateForOnlineDriver.keys("Active-driver:*");
        if(redisKeys==null || redisKeys.isEmpty()) return;
        LocalDateTime currentTime = LocalDateTime.now();
        for(String Key : redisKeys){
            Object ob =  redisTemplateForOnlineDriver.opsForHash().get(Key,"updatedAt");
            if(ob==null) continue;
            LocalDateTime updatedAt = LocalDateTime.parse(ob.toString());
            if(updatedAt.isBefore(currentTime.minusMinutes(1))){
                redisTemplateForOnlineDriver.delete(Key);
            };
            log.info("Removed inactive driver {}", Key);
        }
    }

}