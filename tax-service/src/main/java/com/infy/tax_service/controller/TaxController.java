package com.infy.tax_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.tax_service.exception.ErrorResponse;
import com.infy.tax_service.service.TaxService;

@RestController
@RequestMapping("/api/taxservice")
public class TaxController {

	private final Logger log=LoggerFactory.getLogger(TaxController.class);
	@Autowired
	TaxService service;
	
    @GetMapping("/getTax/{totalAmount}")
    public ResponseEntity<Object> getTax(@PathVariable(required = false) Double totalAmount) {
        log.info("Received request to get tax for totalAmount: {}", totalAmount);

        // Check for invalid input, such as null or non-positive values
        if (totalAmount == null || totalAmount <= 0) {
            log.warn("Invalid totalAmount received: {}", totalAmount);
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid totalAmount", "Total amount must be greater than zero.")); // Returning 400 Bad Request
        }

        try {
            // Calling the service to calculate the tax
            Double tax = service.getTax(totalAmount);

            if (tax == null) {
                log.error("Tax calculation returned null for totalAmount: {}", totalAmount);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(new ErrorResponse("Tax Calculation Error", "Failed to calculate tax for the given amount.")); // Internal Server Error if calculation fails
            }

            log.info("Calculated tax: {} for totalAmount: {}", tax, totalAmount);
            return ResponseEntity.ok(tax); // Returning 200 OK with the tax value
        } catch (Exception e) {
            // Log the error with exception details
            log.error("Error while calculating tax for totalAmount: {}", totalAmount, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ErrorResponse("Internal Server Error", "An unexpected error occurred while calculating tax.")); // Returning 500 Internal Server Error if exception occurs
        }
    }
}
