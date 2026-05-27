package com.sawari.sawari.ForDriver.service;
import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import com.sawari.sawari.dto.OtpPojo;
import com.sawari.sawari.dto.PhoneNumber;
import com.sawari.sawari.service.OtpGeneratorAndSenderService;
import com.sawari.sawari.utiils.JwtUtil;
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
    private RedisTemplate<String,Object> redisTemplateForOnlineDriver;
    public void saveDriver(Driver requestBody){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        requestBody.setOtp(otpGeneratorAndSenderService.GenerateOtp()); //random hardcoded
        requestBody.setIsOnline(false);
        requestBody.setIsOnRide(false);
        requestBody.setIsVerified(false);
        requestBody.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5)); //set otp expired at
        driverRepository.save(requestBody);
    }
    public String VerifyOtp(OtpPojo requestBody,Integer driverId){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        Driver existedDriver = driverRepository.findById(driverId).orElseThrow(()->new RuntimeException("Driver not found"));
        if(existedDriver.getIsVerified()){
            throw new RuntimeException("⚠ Driver is already verified");
        }
        if(LocalDateTime.now().isAfter(existedDriver.getOtpExpiredAt())){
            throw new RuntimeException("Otp has expired⌛");
        }
        if(!existedDriver.getOtp().equals(requestBody.getOtp())){
            throw new RuntimeException("❌ Invalid OTP! please try again later");
        }
        //if everything okay then navigate to next phase
        existedDriver.setIsVerified(true);
        existedDriver.setIsOnline(true);
        existedDriver.setOtp("");
        driverRepository.save(existedDriver);

        updateinRedis(existedDriver); //after Successfull verification put the Rider to redis
        return jwtUtil.GenerateJwtToken(existedDriver.getName());
    }
    public Driver loginService(PhoneNumber phNo){
        if(phNo==null) throw new RuntimeException("Invalid Request");
        Driver existedDriver = driverRepository.findDriverByPhoneNumber(String.valueOf(phNo.getNumber()));
        if(existedDriver==null) throw new RuntimeException("Driver not found");
        if(existedDriver.getIsVerified()) throw new RuntimeException("Driver is already verified");
        existedDriver.setOtp(otpGeneratorAndSenderService.GenerateOtp());
        driverRepository.save(existedDriver);
        return existedDriver;
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