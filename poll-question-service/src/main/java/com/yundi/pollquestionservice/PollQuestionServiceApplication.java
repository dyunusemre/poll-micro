package com.yundi.pollquestionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PollQuestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollQuestionServiceApplication.class, args);
	}

}
