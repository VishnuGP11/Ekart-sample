package com.infy.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.checkout.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}