package com.yundi.polluserservice.recaptcha;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRecaptcha {
    boolean isEnabled();
}
