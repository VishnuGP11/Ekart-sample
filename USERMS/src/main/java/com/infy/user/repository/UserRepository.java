package com.infy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String email);
	
	boolean existsByPhone(String phone);

	boolean existsByUsername(String username);

}
