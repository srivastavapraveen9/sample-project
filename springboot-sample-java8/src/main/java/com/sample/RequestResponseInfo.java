package com.sample;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class RequestResponseInfo extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            long startTime = System.currentTimeMillis();
            MDC.put("traceId", UUID.randomUUID().toString());

            System.out.println("Request URI: " + request.getRequestURL());
            // continue request flow
            filterChain.doFilter(request, response);

            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("Method: {} and MSURL: {} and status: {}", request.getMethod(), request.getRequestURL(), response.getStatus(),
                    kv("MSURL", request.getRequestURL()),
                    kv("method", request.getMethod()),
                    kv("status", response.getStatus()));
            System.out.println("Response Status: " + response.getStatus());
            System.out.println("Time Taken: " + timeTaken + " ms");
        } finally {
            MDC.clear();
        }

    }
}
