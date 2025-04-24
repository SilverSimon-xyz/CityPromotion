package com.example.user_spring.security.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CorsLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CorsLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String origin = httpRequest.getHeader("Origin");
        String method = httpRequest.getMethod();

        //Log CORS request details
        logger.info("CORS Request: Origin = {}, Method = {}", origin, method);

        //If preflight request, log headers
        if("OPTIONS".equalsIgnoreCase(method)) {
            logger.info("CORS Preflight Request - Headers: {}", httpRequest.getHeaderNames());
        }
        filterChain.doFilter(servletRequest, servletResponse);

        //Log CORS response headers
        logger.info("CORS Preflight Response - Headers: {}", httpResponse.getHeaderNames());
    }
}
