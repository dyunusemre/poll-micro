package com.yundi.polluserservice.exception;

import com.yundi.polluserservice.common.BaseCommonRuntimeException;
import com.yundi.polluserservice.common.ErrorCode;

public class InvalidRecaptchaException extends BaseCommonRuntimeException {

    public InvalidRecaptchaException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
