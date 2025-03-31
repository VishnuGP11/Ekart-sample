package com.infy.service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.infy.dto.CartIteamDto;

import com.infy.exception.CartException;

public interface CartService {


	public String addItemToCart(CartIteamDto cartIteamDto) throws CartException;

	public String updateItem(CartIteamDto cartIteamDto) throws CartException;

	public int clearCartByIds(List<Long> cartIds) throws CartException;

	public String clearCartById(Long cartId) throws CartException;

	public List<CartIteamDto> findAllActiveCartDtls(Long userID) throws CartException;



}
