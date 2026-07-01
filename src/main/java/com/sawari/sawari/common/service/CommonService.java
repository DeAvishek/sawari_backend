package com.sawari.sawari.common.service;
import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import com.sawari.sawari.common.dto.OtpPojo;
import com.sawari.sawari.common.dto.OtpSession;
import com.sawari.sawari.common.dto.PhoneNumber;
import com.sawari.sawari.common.utiils.JwtUtil;
import com.sawari.sawari.forRider.Repository.RiderRepository;
import com.sawari.sawari.forRider.entity.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    @Autowired
    private CommonRedisService commonRedisService;

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public OtpSession loginService(PhoneNumber phNo){
        if(phNo==null) throw new RuntimeException("Invalid Request");
//        String otp = otpGeneratorAndSenderService.GenerateOtp();
        String phoneNumber = phNo.getPhoneNumber();
        OtpSession session = new OtpSession();
        session.setPhoneNumber(phoneNumber);
        session.setOtp("123456");
//        otpGeneratorAndSenderService.SendOtp(phoneNumber,"123456");
        //end
        commonRedisService.AddOtpSession(session); //add to redis with ttl
        System.out.println("Phonenumber" + phoneNumber +" "+"and otp" +123456); //todo to remove
        return session;
    }

    public String refreshTokenServiceForDriver(String token){
        int id = commonRedisService.verifyRefreshToken(token);
        if(0==id){
            throw new RuntimeException("Invalid Refresh-token");
        }
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Driver not found"
                        )
                );
        commonRedisService.deleteRefreshToken(token);//delete the refresh token
        String ref_token = commonRedisService.setRefreshTokenForDriver(driver);//create new refresh token
        return ref_token+"#"+jwtUtil.GenerateJwtToken(driver.getUserName()); //return both tokens
    }
    public String refreshTokenServiceForRider(String token){
        int id = commonRedisService.verifyRefreshToken(token);
        if(0==id){
            throw new RuntimeException("Invalid Refresh-token");
        }
        Rider rider = riderRepository.findById(id)
                .orElseThrow( () -> new RuntimeException(
                        "Driver not found"
                ));
        commonRedisService.deleteRefreshToken(token);
        String ref_token = commonRedisService.setRefreshTokenForRider(rider);
        return ref_token+"#"+jwtUtil.GenerateJwtToken(rider.getUserName());
    }

    public String VerifyOtpForDriver(OtpPojo requestBody, String phoneNumber){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        //first have to check in redis is existed or not
        boolean isVerfied = commonRedisService.checkOtpInRedis(phoneNumber,requestBody.getOtp());
        if(!isVerfied){
            throw new RuntimeException("Invalid Request");
        }
        //then check if it is exited in db or not
        Driver existedDriver = driverRepository.findDriverByPhoneNumber(phoneNumber);
        if(existedDriver==null){
            //if user is not exist then create the user and then make a both tokens
            return "";
        }
        //user all ready exited in db return its info with jwt and refresh-token
        return existedDriver.getId()+"#"+
                existedDriver.getUserName()+"#"+
                jwtUtil.GenerateJwtToken(existedDriver.getUserName())+"#"+
                commonRedisService.setRefreshTokenForDriver(existedDriver);
    }
    public String VerifyOtpForRider(OtpPojo requestBody, String phoneNumber){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        //first have to check in redis is existed or not
        boolean isVerfied = commonRedisService.checkOtpInRedis(phoneNumber,requestBody.getOtp());
        if(!isVerfied){
            throw new RuntimeException("Invalid Request");
        }
        //then check if it is exited in db or not
        Rider existedRider = riderRepository.findRiderByPhoneNumber(phoneNumber);
        if(existedRider==null){
            //if user is not exist then create the user and then make a both tokens
            return "";
        }
        //user all ready exited in db return its info with jwt and refresh-token
        return existedRider.getId()+"#"+
                existedRider.getUserName()+"#"+
                jwtUtil.GenerateJwtToken(existedRider.getUserName())+"#"+
                commonRedisService.setRefreshTokenForRider(existedRider);
    }

}
