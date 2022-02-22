package com.yundi.pollauthservice.producer;

import com.yundi.pollauthservice.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String BINDING_NAME = "userRegistration";
    private final StreamBridge streamBridge;

    public void sendRegisteredUserMessage(RegisterRequest registerRequest) {
        streamBridge.send(BINDING_NAME, getUserData(registerRequest));
        log.info("Created User Information sent {}", registerRequest);
    }

    private UserData getUserData(RegisterRequest registerRequest) {
        return UserData.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .name(registerRequest.getName())
                .lastName(registerRequest.getLastName())
                .build();
    }
}
