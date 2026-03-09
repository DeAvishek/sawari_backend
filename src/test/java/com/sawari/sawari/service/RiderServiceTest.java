package com.sawari.sawari.service;

import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.pojo.OtpPojo;
import com.sawari.sawari.utiils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RiderServiceTest {
    @Mock
    RiderRepository riderRepository;

    @Mock
    OtpGeneratorAndSenderService otpGeneratorAndSenderService;

    @InjectMocks
    RiderService riderService;
    @Test
    void createRiderShouldAddRiderSuccessfully()
    {
        Rider rider = new Rider();
        rider.setId(2);
        rider.setUserName("Test1");
        rider.setPhoneNumber("123456789");
        rider.setRole("RIDER");
        rider.setIsVerified(true);
        Mockito.when(otpGeneratorAndSenderService.GenerateOtp()).thenReturn("12345");
        Mockito.when(riderRepository.save(rider)).thenReturn(rider);
        Rider addedRider = riderService.CreateRider(rider);
        //original test
        Assertions.assertEquals(rider.getId(), addedRider.getId());
    }

    @Mock
    JwtUtil jwtUtil;

    @Test
    void VerifyOtpShouldVerifyRider()
    {
        Rider rider = new Rider();
        rider.setId(2);
        rider.setUserName("Test1");
        rider.setPhoneNumber("123456789");
        rider.setRole("RIDER");
        rider.setIsVerified(false);
        rider.setOtp("123454");
        OtpPojo otp = new OtpPojo();
        otp.setOtp("123454");
        Mockito.when(riderRepository.findById(rider.getId())).thenReturn(Optional.of(rider));
        Mockito.when(jwtUtil.GenerateJwtToken(rider.getUserName())).thenReturn("mocked-jwt-token");
        Mockito.when(riderRepository.save(rider)).thenReturn(rider);

        String token = riderService.verifyOtp(otp,2);
        Assertions.assertEquals(token,"mocked-jwt-token");
        Assertions.assertTrue(rider.getIsVerified());

    }
    //test for when user is not find
    @Test
    void VerifyOtpWhenNotFindUser()
    {
        OtpPojo otp = new OtpPojo();
        otp.setOtp("123454");
        Mockito.when(riderRepository.findById(1)).thenReturn(Optional.empty());

        String result = riderService.verifyOtp(otp,1);
        Assertions.assertEquals("", result);

    }
    @Test
    void VerifyOtpWhenUserIsVerified(){
        Rider rider = new Rider();
        rider.setId(2);
        rider.setUserName("Test1");
        rider.setPhoneNumber("123456789");
        rider.setRole("RIDER");
        rider.setIsVerified(true);
        rider.setOtp("123454");
        OtpPojo otp = new OtpPojo();
        otp.setOtp("123454");
        Mockito.when(riderRepository.findById(2)).thenReturn(Optional.of(rider));

        String result = riderService.verifyOtp(otp,2);
        Assertions.assertEquals("", result);
    }

    @Test
    void VerifyOtpWhenOtpIsMisMatch(){
        Rider rider = new Rider();
        rider.setId(2);
        rider.setUserName("Test1");
        rider.setPhoneNumber("123456789");
        rider.setRole("RIDER");
        rider.setIsVerified(false);
        rider.setOtp("123454");
        OtpPojo otp = new OtpPojo();
        otp.setOtp("123452");
        Mockito.when(riderRepository.findById(2)).thenReturn(Optional.of(rider));

        String result = riderService.verifyOtp(otp,2);
        Assertions.assertEquals("", result);
    }
}
