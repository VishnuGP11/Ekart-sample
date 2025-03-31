package com.infy.checkout.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrdersConfig {

	@Bean @LoadBalanced
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
