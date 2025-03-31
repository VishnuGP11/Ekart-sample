package com.springboot.catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Catalog Service Rest APis", description = "This service is to interact with Catalog related issues", version = "v1.0", contact = @Contact(name = "Nishi Kanta Behera", email = "nishikanta.behera@infosys.com", url = "www.google.com"), license = @License(name = "apache maven", url = "maven.com/license")), externalDocs = @ExternalDocumentation(description = "Catalog Service Docs", url = "www.google.com"))

@SpringBootApplication
public class CatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

}
