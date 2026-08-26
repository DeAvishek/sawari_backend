package com.sawari.sawari.common.service.RateLimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthRateLimiter {
    @Qualifier("authLimiter")
    @Autowired
    private RateLimiter rateLimiter;

    public boolean isRequestInsideThresholdForAuth()
    {
        return rateLimiter.acquirePermission();
    }
}
