package com.sample.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sample.entity.Person;

@FeignClient(name = "order-service", url = "http://localhost:8081")
public interface OrderServiceClient {

	@GetMapping("/api/order")
	List<Person> getOrderDetails();

}
