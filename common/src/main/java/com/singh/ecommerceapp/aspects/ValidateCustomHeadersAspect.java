package com.singh.ecommerceapp.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class ValidateCustomHeadersAspect {

    @Before("@within(validateCustomHeaders) || @annotation(validateCustomHeaders)")
    public void validateHeaders(JoinPoint joinPoint, ValidateCustomHeaders validateCustomHeaders) {
        if (validateCustomHeaders == null) {
            validateCustomHeaders = joinPoint.getTarget().getClass().getAnnotation(ValidateCustomHeaders.class);
        }

        if (validateCustomHeaders == null) {
            // No annotation present, skip validation
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String[] mandatoryHeaders = validateCustomHeaders.mandatoryHeaders();
        String[] optionalHeaders = validateCustomHeaders.optionalHeaders();

        for (String header : mandatoryHeaders) {
            String headerValue = request.getHeader(header);
            log.info("headerValue :{}",headerValue);
            if (request.getHeader(header) == null) {
                throw new IllegalArgumentException("Missing mandatory header: " + header);
            }
        }

        for (String header : optionalHeaders) {
            String headerValue = request.getHeader(header);
            // Optional header is present, you can perform further validation if needed
        }
    }
}

