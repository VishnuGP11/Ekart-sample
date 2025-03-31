package com.infy.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.checkout.entity.CartItem;



public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
