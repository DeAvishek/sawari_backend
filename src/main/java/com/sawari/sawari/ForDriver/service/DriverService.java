package com.sawari.sawari.ForDriver.service;
import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import com.sawari.sawari.dto.OtpPojo;
import com.sawari.sawari.dto.OtpSession;
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
    public Driver saveDriver(Driver requestBody){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        return driverRepository.save(requestBody);
    }
    public String VerifyOtp(OtpPojo requestBody,String phoneNumber){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        //first have to check in redis is existed or not
        boolean isVerfied = redisServiceForDriver.checkOtpInRedis(phoneNumber,requestBody.getOtp());
        if(!isVerfied){
            throw new RuntimeException("Invalid Request");
        }
        //then check if it is exited in db or not
        Driver existedDriver = driverRepository.findDriverByPhoneNumber(phoneNumber);
        if(existedDriver==null){
            //ask for username and create the user and then make a jwt
            return "";
        }
        //user all ready exited in db return its ifo with jwt
        return existedDriver.getId()+"#"+existedDriver.getUserName()+"#"+jwtUtil.GenerateJwtToken(existedDriver.getUserName());


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
    //login service base on otp session
    public OtpSession loginService(PhoneNumber phNo){
        if(phNo==null) throw new RuntimeException("Invalid Request");
        //generate and send otp
//        String otp = otpGeneratorAndSenderService.GenerateOtp();
        String phoneNumber = phNo.getPhoneNumber();
        OtpSession session = new OtpSession();
        session.setPhoneNumber(phoneNumber);
        session.setOtp("123456");
//        otpGeneratorAndSenderService.SendOtp(phoneNumber,"123456");
        //end
        redisServiceForDriver.AddOtpSession(session); //add to redis with ttl
        System.out.println("Phonenumber" + phoneNumber +" "+"and otp" +123456); //todo to remove
        return session;

    }
}