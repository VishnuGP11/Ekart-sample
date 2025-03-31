package com.infy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infy.dto.CartIteamDto;


public interface CartRepository extends JpaRepository<CartIteamDto, Long>{

	public List<CartIteamDto> findByUserId(Long userId);
	
	public Optional<CartIteamDto> findByUserIdAndProductId(Long userId, Long productId);

	@Modifying
	@Query(value = "UPDATE   cart_iteam  set is_active=0,is_order_placed=1  WHERE cart_id IN (:cartId)", nativeQuery = true)
 
	int clearCartByIds(@Param("cartId") List<Long> cartId);
	
	@Query(value = "select * from cart_iteam where is_active=1 and user_id =:userId ORDER BY product_id ASC", nativeQuery = true)
	Optional<List<CartIteamDto>> findAllActiveCartDtls(@Param("userId") Long userID);
 
	

}
