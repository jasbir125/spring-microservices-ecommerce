package com.singh.ecommerceapp.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final long WINDOW_SIZE_IN_MILLIS = 60_000; // 1 minute

    private static class ClientRequestInfo {
        AtomicInteger requestCount = new AtomicInteger(0);
        long windowStartTime;

        ClientRequestInfo() {
            this.windowStartTime = System.currentTimeMillis();
        }
    }

    // Store client IP -> request info with timestamp and count
    private final Map<String, ClientRequestInfo> requestCountsPerIpAddress = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();

        // Get or create request info for client IP
        ClientRequestInfo requestInfo = requestCountsPerIpAddress.computeIfAbsent(clientIp, k -> new ClientRequestInfo());

        synchronized (requestInfo) {
            long currentTime = System.currentTimeMillis();

            // Check if current window expired
            if (currentTime - requestInfo.windowStartTime > WINDOW_SIZE_IN_MILLIS) {
                // Reset window start time and count
                requestInfo.windowStartTime = currentTime;
                requestInfo.requestCount.set(0);
            }

            int currentCount = requestInfo.requestCount.incrementAndGet();

            if (currentCount > MAX_REQUESTS_PER_MINUTE) {
                httpResponse.setStatus(429);
                httpResponse.getWriter().write("Too many requests. Please try again later.");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
