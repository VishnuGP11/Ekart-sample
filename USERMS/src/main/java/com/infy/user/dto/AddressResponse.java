package com.infy.user.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {
	private String status;
    private List<AddressDTO> address;
    private String message;
 
    // Constructor for success response
    public AddressResponse(String status, List<AddressDTO> address) {
        this.status = status;
        this.address = address;
        this.message = null; // Explicitly set message to null for success
    }
 
    // Constructor for error response
    public AddressResponse(String status, String message) {
        this.status = status;
        this.address = null; // Explicitly set address to null for error
        this.message = message;
    }
 
    // Getters
    public String getStatus() {
        return status;
    }
 
    public List<AddressDTO> getAddress() {
        return address;
    }
 
    public String getMessage() {
        return message;
    }
}