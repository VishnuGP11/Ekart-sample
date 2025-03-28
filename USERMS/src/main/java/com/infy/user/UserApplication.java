package com.infy.user;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "User Service REST APIs",
				description = "User Service RST APIs documentation"))

@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {

	@Bean
    public ModelMapper modelMapper()   {
        return new ModelMapper();
    }
	
	@Bean
	public RestTemplate restTemplate(){
        return new RestTemplate();
	  
	}
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
