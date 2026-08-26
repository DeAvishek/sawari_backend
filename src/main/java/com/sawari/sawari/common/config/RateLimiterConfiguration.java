package com.sawari.sawari.common.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {
    @Bean("authLimiter")
    public RateLimiter authRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(3))
                .limitForPeriod(1)
                .timeoutDuration(Duration.ZERO)
                .build();
        return RateLimiter.of("authLimiter", config);
    }

    @Bean
    public RateLimiter tripRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(2)
                .timeoutDuration(Duration.ZERO)
                .build();
        return RateLimiter.of("tripLimiter", config);
    }
}
