package com.sawari.sawari.service;

import com.sawari.sawari.pojo.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.sawari.sawari.pojo.Geocode;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Geocode> redisTemplateForGeocode;

    @Autowired
    private RedisTemplate<String,DirectionResponse> redisTemplateForGeometry;
    public void setToRedis(String key, Geocode value, long ttl, TimeUnit timeUnit){
        try{
            System.out.println("Set to redis "+key);
            redisTemplateForGeocode.opsForValue().set(key, value, ttl, timeUnit);
        }catch (Exception e){
            log.error("Failed to set key {} in Redis", e.getMessage());
        }
    }
    public Geocode getFromRedis(String key){
        try{
             return redisTemplateForGeocode.opsForValue().get(key);
        }catch (Exception e){
            log.error("Failed to get key {} in Redis", e.getMessage());
            return null;
        }
    }

    public void SetToRedisGeometry(String key, DirectionResponse value, long ttl, TimeUnit timeUnit){
        try{
            System.out.println("Set to redis ");
            redisTemplateForGeometry.opsForValue().set(key, value, ttl, timeUnit);
        }catch (Exception e){
            log.error("Failed to set Geomatry in Redis", e.getMessage());
        }
    }
    public DirectionResponse getFromRedisGeometry(String key){
        try{
            return redisTemplateForGeometry.opsForValue().get(key);
        }catch (Exception e){
            log.error("Failed to get Geometry from Redis", e.getMessage());
            return null;
        }
    }
}
