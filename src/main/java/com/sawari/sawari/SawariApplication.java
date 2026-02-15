package com.sawari.sawari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SawariApplication {

	public static void main(String[] args) {
		SpringApplication.run(SawariApplication.class, args);
	}

}
