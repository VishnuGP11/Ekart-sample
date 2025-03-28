package com.infy.user.entity;


import java.time.LocalDateTime;
 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
 
@Entity
@Table(name = "address", schema = "user_module")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    @ManyToOne 
    @JoinColumn(name = "user_id", 
    nullable = false, 
    foreignKey = @ForeignKey(name = "fk_user_id")) 
    private User user; 
    @Column(name = "street", length = 255) 
    private String street;
    @Column(name = "addressLine", length = 100) 
    private String addressLine;
    @Column(name = "ADDRESS_TYPE", nullable = false, length = 20)
    @Pattern(regexp = "S|B", message = "Address type must be either 'S' or 'B'")
    private String addressType;
    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "state", length = 100)
    private String state;
    @Column(name = "zipcode", length = 100) 
    private String zipcode;
    @Column(name = "country", length = 100) 
    private String country;
	@Column(name = "isPrimary") 
    private boolean isPrimary;
    @Column(name = "creation_date", updatable = false) 
    private LocalDateTime creationDate = LocalDateTime.now();
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();
 
	public Address() {
	}
	public Address(Long addressId, User user, String street, String addressLine, String addressType,
			String city, String state, String zipcode, String country, LocalDateTime creationDate,
			LocalDateTime lastUpdated) {
		super();
		this.addressId = addressId;
		this.user = user;
		this.street = street;
		this.addressLine = addressLine;
		this.addressType = addressType;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
	}

	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
    public boolean isPrimary() {
		return isPrimary;
	}
 
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
 
	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
 
 
}
