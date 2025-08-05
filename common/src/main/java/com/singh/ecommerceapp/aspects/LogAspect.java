package com.singh.ecommerceapp.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;

    @Around("@annotation(com.singh.ecommerceapp.aspects.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        if (request instanceof ContentCachingRequestWrapper wrappedRequest) {
            logRequestDetails(request);
            logRequestBody(wrappedRequest);
        }
        // Proceed with method execution and get the response
        Object response = joinPoint.proceed();
        // Log Response details
        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpServletRequest request) {
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Client IP: {}", request.getRemoteAddr());
        // Log request headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Request Header: {}={}", headerName, headerValue);
        }
        // Log request parameters
        request.getParameterMap().forEach((key, value) -> log.info("Request Param: {}={}", key, value));
    }

    private void logResponseDetails(Object response) {
        if (response instanceof ResponseEntity<?> responseEntity) {
            log.info("Response Status: {}", responseEntity.getStatusCode());
            HttpHeaders headers = responseEntity.getHeaders();
            headers.forEach((key, value) -> log.info("Response Header: {}={}", key, value));
            log.info("Response Body: {}", responseEntity.getBody());
        } else {
            log.info("Response Object: {}", response);
        }
    }
    private void logRequestBody(ContentCachingRequestWrapper request) {
        try {
            String body = new String(request.getContentAsByteArray(), request.getCharacterEncoding());

            String jsonResponse = objectMapper.writeValueAsString(body);
            //log.info("Response Body: {}", jsonResponse);

            log.info("Request Body: {}", body);
        } catch (UnsupportedEncodingException e) {
            log.error("Error reading request body", e);
        } catch (JsonProcessingException e) {
            log.error("Error reading request body.", e);
            throw new RuntimeException(e);
        }
    }
}
