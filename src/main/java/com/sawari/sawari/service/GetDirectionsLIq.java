package com.sawari.sawari.service;

import com.sawari.sawari.helper.StringFormatter;
import com.sawari.sawari.pojo.DirectionResponse;
import com.sawari.sawari.pojo.Route;
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
public class GetDirectionsLIq {
    @Value("${LocationIq.account.api}")
    private String LocationIQApiKey; //todo

    @Autowired
    private StringFormatter stringFormatter;
    @Autowired
    private RedisService redisService;
    private static final String Url = "https://us1.locationiq.com/v1/directions/";
    //hardcoded need to update
    private Double srcLat = 51.514156;
    private Double srcLon = -0.12070277;
    private Double dstLat = 51.507996;
    private Double dstLon = -0.12360937;
    public ResponseEntity<DirectionResponse> getDirections() {
        try{
            String uri = String.format(
                    "%sdriving/%f,%f;%f,%f?key=%s&steps=true&alternatives=true&geometries=polyline&overview=full",
                    Url,
                    srcLon,
                    srcLat,
                    dstLon,
                    dstLat,
                    LocationIQApiKey
            );
            String redisKeyForSrcDestGeocode = stringFormatter.FormatStringForSrcDestGeocode(srcLon, srcLat, dstLon, dstLat);
            DirectionResponse GeometryFromRedis = redisService.getFromRedisGeometry(redisKeyForSrcDestGeocode);
            if(GeometryFromRedis != null){
                System.out.println("get it from redis");
                return new ResponseEntity<>(GeometryFromRedis, HttpStatus.OK);
            }
            RestTemplate restTemplate = new RestTemplate();
            DirectionResponse response =  restTemplate.getForObject(uri, DirectionResponse.class);
            redisService.SetToRedisGeometry(redisKeyForSrcDestGeocode,response,24, TimeUnit.HOURS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error while fetching directions", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
