package com.infy.checkout.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(contact = @Contact(name = "Rifat Bano", email = "rifat.bano@infosys.com"),
		description = "Api documentation for Order Checkout",
		title = "OpenApi specification - Checkout", version = "1.0", 
		termsOfService = "terms of service"))
public class OpenApiConfig {

}
