package com.sawari.sawari.common.config;

import com.sawari.sawari.common.dto.DirectionResponse;
import com.sawari.sawari.common.dto.Geocode;
import com.sawari.sawari.common.dto.OtpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    //    @Autowired
//    private RedisRideRequestSubscriber redisRideRequestSubscriber;
    @Bean
    public RedisTemplate<String, Geocode> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Geocode> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Geocode.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, DirectionResponse> redisTemplate2(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, DirectionResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(DirectionResponse.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //
    @Bean
    public RedisTemplate<String, String> redisTemplate3ForAutocomplete(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate4ForRideSessionAndOnlineDrivers(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new JacksonJsonRedisSerializer<>(Object.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> driverGeoRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //redis config for Otp session
    @Bean
    public RedisTemplate<String, OtpSession> redisTemplateForOtpSession(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, OtpSession> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(OtpSession.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //for pub/sub
//    @Bean
//    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(redisConnectionFactory);
//        container.addMessageListener(messageListener(),channelTopic());
//        return container;
//    }

//    @Bean
//    public ChannelTopic channelTopic() {
//        return new ChannelTopic("ride_req");
//    }
//    @Bean
//    public MessageListenerAdapter messageListener() {
//        return new MessageListenerAdapter(
//                redisRideRequestSubscriber,
//                "onMessage"
//        );
//    }
}
