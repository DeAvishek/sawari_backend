package com.sawari.sawari.forRider.service.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sawari.sawari.common.dto.AutocompleteLocation;
import com.sawari.sawari.common.dto.DirectionResponse;
import com.sawari.sawari.common.support.EnumValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.sawari.sawari.common.dto.Geocode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceForRider {
    @Autowired
    private RedisTemplate<String, Geocode> redisTemplateForGeocode;

    @Autowired
    private RedisTemplate<String,DirectionResponse> redisTemplateForGeometry;

    @Autowired
    private RedisTemplate<String, String> redisTemplateForAutocomplete;

    @Autowired
    private RedisTemplate<String,Object>redisTemplateForRideSession;

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
            redisTemplateForGeometry.opsForValue().set(key, value, ttl, timeUnit);
            System.out.println("Direction Response Set to redis 😊" + LocalDateTime.now());
        }catch (Exception e){
            log.error("Failed to set Geomatry in Redis", e.getMessage());
        }
    }
    public DirectionResponse getFromRedisGeometry(String key){
        try{
            log.info("Direction response get from redis "+LocalDateTime.now());
            return redisTemplateForGeometry.opsForValue().get(key);
        }catch (Exception e){
            log.error("Failed to get Geometry from Redis", e.getMessage());
            return null;
        }
    }

    //for Autocomplete
    @Autowired
    private ObjectMapper  objectMapper;
    public void setAutocompleteToRedis(String key, List<AutocompleteLocation>result,long ttl, TimeUnit timeUnit){
        try{
            //before store convert to string
            redisTemplateForAutocomplete.opsForValue().set(key,objectMapper.writeValueAsString(result),ttl,timeUnit);
            System.out.println("Autocomplete suggestion Set to redis 😍" +key);
        }catch (Exception e){
            log.error("Failed to set key {} in Redis 😔" , e.getMessage());
        }
    }
    //get from redis
    public List<AutocompleteLocation> getAutocompleteFromRedis(String key){
        try {
            String cached = redisTemplateForAutocomplete.opsForValue().get(key);
            if(cached==null){
                throw new Exception("No such autocomplete key");
            }
            System.out.println("Get from redis autocomplete Suggestion 😍");
            return objectMapper.readValue(cached, new TypeReference<ArrayList<AutocompleteLocation>>(){});
        }catch (Exception e){
            log.warn("Failed to get key {} in Redis 😔", e.getMessage());
            return new ArrayList<>();
        }
    }

    //Part 4 --- for create a Ride session in redis
    public void createRideSession(Integer riderId,Long rideId,String pickup,String drop){
        try {
            String Key = "ride:session:"+rideId;
            redisTemplateForRideSession.opsForHash().put(Key,"riderId",riderId);
            redisTemplateForRideSession.opsForHash().put(Key,"driverId","");
            redisTemplateForRideSession.opsForHash().put(Key,"otp","123456"); //otp is hard coded
            redisTemplateForRideSession.opsForHash().put(Key,"pickup",pickup);
            redisTemplateForRideSession.opsForHash().put(Key,"destination",drop);
            redisTemplateForRideSession.opsForHash().put(Key,"status",EnumValues.TripStatusEnum.Requested.name());
            redisTemplateForRideSession.opsForHash().put(Key,"createdAt", Instant.now());
            redisTemplateForRideSession.opsForHash().put(Key,"updatedAt", Instant.now());
            redisTemplateForRideSession.expire(Key,2,TimeUnit.HOURS);
            log.info("Successfully Created Ride Session😍");
        }catch (Exception e){
            log.error("Failed to create ride Session 😔",e.getMessage());
        }
    }

}
