package com.yundi.polluserservice.recaptcha;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RecaptchaInterceptor {

    private final RecaptchaService recaptchaService;

    @Around("@annotation(requiredRecaptcha)")
    public Object validateRecaptcha(ProceedingJoinPoint joinPoint, RequiredRecaptcha requiredRecaptcha) throws Throwable {
        if (requiredRecaptcha.isEnabled())
            recaptchaService.validateRecaptcha(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        return joinPoint.proceed();
    }

}
