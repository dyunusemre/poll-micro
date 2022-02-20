package com.yundi.polluserservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR("1", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("2", HttpStatus.BAD_REQUEST),
    FORBIDDEN("3", HttpStatus.FORBIDDEN);

    private final String code;
    private final HttpStatus httpStatus;
}
