package com.sawari.sawari.forRider.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisRideRequestPublisher implements RedisPublisher {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(),message);
    }
}
