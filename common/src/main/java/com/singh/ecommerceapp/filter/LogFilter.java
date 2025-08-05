package com.singh.ecommerceapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		LocalDateTime date = LocalDateTime.now();
        log.debug("LogFilter: {} - {}:{}{}", date, httpRequest.getLocalAddr(), httpRequest.getLocalPort(), httpRequest.getServletPath());
		Enumeration<String> headers = httpRequest.getHeaderNames();
		while (headers.hasMoreElements()) {
			String headerName = headers.nextElement();
            log.debug("\tHeader: {}:{}", headerName, httpRequest.getHeader(headerName));
		}
		chain.doFilter(request, response);

	}

}
