package com.global.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.entity.Cart;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepo extends JpaRepository<Cart, String>{
	
	void deleteByLastModifiedDateBefore(LocalDateTime cutOff);
	
//	@Transactional
//	void deleteBySessionId(String sessionId);
	
//	Cart findBySessionId(String sessionId);
	
	Cart findByCid(String cid);
	
	@Transactional
	@Modifying
	@Query("UPDATE Cart c SET c.expTime = :newExpTime WHERE c.cid=:cid")
	void updateExpTime(String cid, LocalDateTime newExpTime);
	
	
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cid IN (SELECT cid FROM Cart c WHERE c.expTime <= :currentDateTime)")
    void deleteExpiredCartsItems(LocalDateTime currentDateTime);
	
	
    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.expTime <= :currentDateTime")
    void deleteExpiredCarts(LocalDateTime currentDateTime);
	
    
//    -- Step 1: Delete items from cart_items where the associated cart is expired
//    DELETE FROM cart_items
//    WHERE cart_id IN (
//        SELECT cid FROM carts WHERE exp_time <= CURRENT_TIMESTAMP
//    );

}
