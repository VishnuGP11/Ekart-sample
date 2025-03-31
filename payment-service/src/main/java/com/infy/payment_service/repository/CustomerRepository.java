package com.infy.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infy.payment_service.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}