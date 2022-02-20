package com.yundi.polluserservice.recaptcha.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecaptchaResponse {

    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    private String hostName;

    @JsonProperty("error-codes")
    private String[] errorCodes;
}
