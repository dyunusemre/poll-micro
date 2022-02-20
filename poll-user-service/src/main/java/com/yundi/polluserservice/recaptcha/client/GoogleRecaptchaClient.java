package com.yundi.polluserservice.recaptcha.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "recaptcha", url = "${recaptcha.client.uri}")
public interface GoogleRecaptchaClient {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PostMapping
    RecaptchaResponse validateRecaptcha(@SpringQueryMap Map<String, String> recaptchaParams);
}
