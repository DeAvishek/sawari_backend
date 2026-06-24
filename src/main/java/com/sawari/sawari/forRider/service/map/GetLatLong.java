package com.sawari.sawari.forRider.service.map;
import com.sawari.sawari.common.dto.Geocode;
import com.sawari.sawari.forRider.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GetLatLong {
    @Autowired
    private RedisService redisService;
    @Value("${LocationIq.account.api}")
    private String LocationIQApiKey; //need to set the Api key in env
    private static final String Url = "https://us1.locationiq.com/v1/";
    public ResponseEntity<String> GetLatAndLongForLocation(String Location){
        try{

            //now the application flow will be
            String loc = Location;
            loc=loc.trim().toLowerCase();
            //->first check the location exist in redis
            Geocode fromRedisGeocode = redisService.getFromRedis(loc);
            //->if yes then return it directly
            if(fromRedisGeocode!=null){
                System.out.println("Hey i Got it from redis");
                return new ResponseEntity<>(fromRedisGeocode.getDisplayName(), HttpStatus.OK);
            }
            //->if not then fetch it and store it in redis and return it
            String uri = String.format(
                    "%ssearch?key=%s&q=%s&format=json&",
                    Url,
                    LocationIQApiKey,
                    Location
            );
            RestTemplate restTemplate = new RestTemplate();
            Geocode[] response = restTemplate.getForObject(uri, Geocode[].class);
            redisService.setToRedis(loc,response[0],24, TimeUnit.HOURS);
            return new ResponseEntity<>(response[0].getDisplayName(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Failed to get Geocode",e.getMessage());
            return new ResponseEntity<>("Failed to get Geocode",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
