package com.yundi.polluserservice.recaptcha;

import com.yundi.polluserservice.domain.common.InvalidRecaptchaException;
import com.yundi.polluserservice.domain.enums.ErrorCode;
import com.yundi.polluserservice.recaptcha.client.GoogleRecaptchaClient;
import com.yundi.polluserservice.recaptcha.client.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RecaptchaService {

    @Value("${recaptcha.secret-key}")
    private String recaptchaSecretKey;

    private final GoogleRecaptchaClient googleRecaptchaClient;

    public void validateRecaptcha(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("g-recaptcha-response");
        validateHeader(header);
        validateCaptchaResponse(header);
    }

    private void validateHeader(String header) {
        if (header == null || header.isEmpty()) {
            throw new InvalidRecaptchaException("Invalid_Recaptcha", ErrorCode.BAD_REQUEST);
        }
    }

    private void validateCaptchaResponse(String header) {
        validateClientResponse(googleRecaptchaClient.validateRecaptcha(getGoogleRecaptchaRequest(header)));
    }

    private void validateClientResponse(RecaptchaResponse recaptchaResponse) {
        if (recaptchaResponse == null)
            throw new InvalidRecaptchaException("Invalid_Recaptcha", ErrorCode.BAD_REQUEST);
        if (!recaptchaResponse.isSuccess())
            throw new InvalidRecaptchaException("Invalid_Recaptcha", ErrorCode.BAD_REQUEST);
    }

    private Map<String, String> getGoogleRecaptchaRequest(String header) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("secret", recaptchaSecretKey);
        requestMap.put("response", header);
        return requestMap;
    }
}
