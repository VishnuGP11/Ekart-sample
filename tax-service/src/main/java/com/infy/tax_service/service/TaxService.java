package com.infy.tax_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaxService {

	private static final Logger log = LoggerFactory.getLogger(TaxService.class);

	public Double getTax(Double orderTotal) {
		if (orderTotal == null) {
			log.warn("Received null orderTotal");
			throw new IllegalArgumentException("Order total cannot be null");
		}

		if (orderTotal <= 0) {
			log.warn("Received invalid orderTotal: {}", orderTotal);
			throw new IllegalArgumentException("Order total must be greater than zero");
		}

		try {
			// Temporary formula for tax calculation: 2% of the order total
			Double tax = orderTotal * 2 / 100;
			log.info("Calculated tax: {} for orderTotal: {}", tax, orderTotal);
			return tax;
		} catch (Exception e) {
			log.error("Error while calculating tax for orderTotal: {}", orderTotal, e);
			throw new RuntimeException("Error while calculating tax", e); // Rethrow as a runtime exception
		}
	}
}