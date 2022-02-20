package com.yundi.polluserservice.domain.common;

import com.yundi.polluserservice.domain.enums.ErrorCode;

public class InvalidRecaptchaException extends BaseCommonRuntimeException {

    public InvalidRecaptchaException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
