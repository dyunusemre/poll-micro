package com.yundi.polluserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class PollUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollUserServiceApplication.class, args);
    }
}
