package com.sawari.sawari.forRider.service.map;

import com.sawari.sawari.common.helper.StringFormatter;
import com.sawari.sawari.common.dto.DirectionResponse;
import com.sawari.sawari.common.dto.SourceDestinationLatAndLong;
import com.sawari.sawari.forRider.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GetDirectionsLIq {
    @Value("${LocationIq.account.api}")
    private String LocationIQApiKey;

    @Autowired
    private StringFormatter stringFormatter;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestTemplate restTemplate;
    private static final String Url = "https://us1.locationiq.com/v1/directions/";

    public DirectionResponse getDirections(SourceDestinationLatAndLong body) {
            Double srcLon = Double.parseDouble(body.getSourceLongitude());
            Double srcLat = Double.parseDouble(body.getSourceLatitude());
            Double dstLon = Double.parseDouble(body.getDestinationLongitude());
            Double dstLat = Double.parseDouble(body.getDestinationLatitude());
            String uri = String.format(
                    "%sdriving/%f,%f;%f,%f?key=%s&steps=true&alternatives=true&geometries=polyline&overview=full",
                    Url,
                    srcLon,
                    srcLat,
                    dstLon,
                    dstLat,
                    LocationIQApiKey
            );
            //first check in redis of present return it
            String redisKeyForSrcDestinationDirection = stringFormatter.FormatStringForSrcDestGeocode(srcLon, srcLat, dstLon, dstLat);
            DirectionResponse GeometryFromRedis = redisService.getFromRedisGeometry(redisKeyForSrcDestinationDirection);
            if(GeometryFromRedis != null){
                return GeometryFromRedis;
            }
            DirectionResponse response =  restTemplate.getForObject(uri, DirectionResponse.class);
            //first time set to redis
            redisService.SetToRedisGeometry(redisKeyForSrcDestinationDirection,response,24, TimeUnit.HOURS);
            return response;
    }
}
