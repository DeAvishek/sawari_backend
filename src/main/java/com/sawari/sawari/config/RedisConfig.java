package com.sawari.sawari.config;

import com.sawari.sawari.pojo.DirectionResponse;
import com.sawari.sawari.pojo.Geocode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Geocode> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Geocode> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Geocode.class));
        redisTemplate.afterPropertiesSet();
        return  redisTemplate;
    }

    @Bean
    public RedisTemplate<String, DirectionResponse>redisTemplate2(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, DirectionResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(DirectionResponse.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    //
    public RedisTemplate<String,String> redisTemplate3ForAutocomplete(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer( new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate4ForRideSessionAndOnlineDrivers(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer( new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer( new JacksonJsonRedisSerializer<>(Object.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
