package com.yundi.polluserservice.consumer;

import com.yundi.polluserservice.domain.model.User;
import com.yundi.polluserservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserService userService;

    @Bean
    Consumer<UserData> userRegistration() {
        return userData -> {
            userService.save(new User(userData));
        };
    }
}
