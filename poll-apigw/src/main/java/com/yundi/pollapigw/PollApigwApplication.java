package com.yundi.pollapigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PollApigwApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollApigwApplication.class, args);
	}

}
