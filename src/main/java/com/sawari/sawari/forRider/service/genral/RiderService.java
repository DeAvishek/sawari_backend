package com.sawari.sawari.forRider.service.genral;

import com.sawari.sawari.forRider.entity.Rider;
import com.sawari.sawari.common.service.CommonRedisService;
import com.sawari.sawari.forRider.Repository.RiderRepository;
import com.sawari.sawari.common.utiils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RiderService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private CommonRedisService redisService;

    public String CreateRider(Rider requestBody){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        
        Rider savedRider = riderRepository.save(requestBody);
        return savedRider.getId()+"#"+
                savedRider.getUserName()+"#"+
                jwtUtil.GenerateJwtToken(savedRider.getUserName())+"#"+
                redisService.setRefreshTokenForRider(savedRider);
    }
}
