package com.infy.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.user.entity.Address;



public interface AddressRepository  extends JpaRepository<Address, Long>{

	List<Address> findByUser_UserIdAndAddressType(Long userId, String addressType);

    List<Address> findAllByUser_UserId(Long userId);

	Address findByUser_UserId(Long userId);
}
