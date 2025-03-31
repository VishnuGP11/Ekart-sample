package com.infy.checkout.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
	 private Long addressId;      // Corresponds to 'id' in AddressEntity
	    private Long userId;         // Corresponds to 'user.id' in AddressEntity
	    private String street;       // Corresponds to 'street' in AddressEntity
	    private String addressLine;  // Corresponds to 'addressLine1' in AddressEntity
	    private String addressType;  // Corresponds to 'addressType' in AddressEntity
	    private String city;         // Corresponds to 'city' in AddressEntity
	    private String state;        // Corresponds to 'state' in AddressEntity
	    private String zipCode;      // Corresponds to 'zipcode' in AddressEntity
	    private String country;      // Corresponds to 'country' in AddressEntity
		private String status;
		private String message;
 
	  
	    public AddressDTO(String status, String message, Long addressId, Long userId) {
			super();
			this.status = status;
			this.message = message;
			this.addressId = addressId;
			this.userId = userId;
		}
	    
 
	    public AddressDTO() {
			// TODO Auto-generated constructor stub
		}

		// Getters and Setters
	    public Long getAddressId() {
	        return addressId;
	    }
 
	    public Long getUserId() {
	        return userId;
	    }
 
	    public String getStreet() {
	        return street;
	    }
 
	    public String getAddressLine() {
	        return addressLine;
	    }
 
	    public String getAddressType() {
	        return addressType;
	    }
 
	    public String getCity() {
	        return city;
	    }
 
	    public String getState() {
	        return state;
	    }
 
	    public String getZipCode() {
	        return zipCode;
	    }
 
	    public String getCountry() {
	        return country;
	    }

		public void setAddressId(Long addressId) {
			this.addressId = addressId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public void setAddressLine(String addressLine) {
			this.addressLine = addressLine;
		}

		public void setAddressType(String addressType) {
			this.addressType = addressType;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setState(String state) {
			this.state = state;
		}

		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}

		public void setCountry(String country) {
			this.country = country;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	    
	    
}