package com.sawari.sawari;

import com.sawari.sawari.common.service.CommonRedisService;
import com.sawari.sawari.common.service.DriverMatchingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableCaching
@SpringBootApplication
@RestController
public class SawariApplication {
    @GetMapping("/")
    public String healthCheck(){
        return "I am fine:)";
    }
	public static void main(String[] args) {
        SpringApplication.run(SawariApplication.class, args);
	}

}
