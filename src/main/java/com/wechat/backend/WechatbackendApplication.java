package com.wechat.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WechatbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatbackendApplication.class, args);
	}
	
}
