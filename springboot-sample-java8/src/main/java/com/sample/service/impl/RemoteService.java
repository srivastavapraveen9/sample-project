//package com.sample.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//
//@Service
//public class RemoteService {
//
//	private int attempt = 1;
//	
//	@CircuitBreaker(name = "myService", fallbackMethod = "fallback")
//    public String callExternalService() {
//        System.out.println("Attempt: " + attempt);
//        attempt++;
//
//        // Simulate failure
//        if (attempt <= 3) {
//            throw new RuntimeException("Service failed!");
//        }
//
//        return "Success on attempt " + attempt;
//    }
//
//    // Fallback method signature must match
//    public String fallback(Exception ex) {
//        return "Fallback response: service is unavailable. " + ex.getMessage();
//    }
//}
