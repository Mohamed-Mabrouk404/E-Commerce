package com.global.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.global.entity.Cart;
import com.global.entity.CartItem;
import com.global.entity.Product;
import com.global.mapper.CartItemMapper;
import com.global.repository.CartItemRepo;
import com.global.repository.CartRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final CartItemMapper cartItemMapper;
	
	public Cart createCart(String cid, LocalDateTime expTime){
		Cart newCart = new Cart();
		newCart.setCid(cid);
		newCart.setExpTime(expTime);
		newCart = cartRepo.save(newCart);
		log.info("->>>>>>>>>>> Cart created successfully");
		return newCart;
	}
	
	public void updateCartExpTime(String cid, LocalDateTime newExpTime) {
		cartRepo.updateExpTime(cid, newExpTime);
	}
	
	public Cart getCartByCid(String cid) {
		return cartRepo.findByCid(cid);
	}
	
	@Transactional
	public ResponseEntity<?> addItemToCart(CartItem item, String cid) {
		Cart cart = getCartByCid(cid);
		for (CartItem cartItem : cart.getCartItems()){
			if(cartItem.getProduct().getId().equals(item.getProduct().getId())) {
				log.info("this item is actually in cart!!");
				return new ResponseEntity<>(cart, HttpStatus.NOT_MODIFIED);
			}
		}
		cart.addItem(item);
		cart = cartRepo.save(cart);
		
		// Find the item with the given ID
		Optional<CartItem> newItem = cart.getCartItems().stream()
				.filter(I -> I.getProduct().getId() == item.getProduct().getId())
				.findFirst();
		
		return ResponseEntity.ok(newItem);
	}
	
	@Transactional
	public Cart removeItemFromCart(Long cartItemId, String cid) {
		Cart cart = getCartByCid(cid);
		
		CartItem cartItem = cartItemRepo.findById(cartItemId).get();
		
		cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
		cart.getCartItems().remove(cartItem);
		cart.setItemsNum(cart.getItemsNum() - 1);
		
		cart = cartRepo.save(cart);
		return cart;
	}
	
	
	// DESCRIPTION: when you need to remove cartItem from cart when you are already in the cart
	@Transactional
	public Cart removeItemFromCart(CartItem item, String cid) {
		Cart cart = getCartByCid(cid);

		for(CartItem cartItem : cart.getCartItems()) {
			if(cartItem.getProduct().getId().equals(item.getProduct().getId())) {
				
				cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
				cart.getCartItems().remove(cartItem);
				cart.setItemsNum(cart.getItemsNum() - 1);
				
				break;
			}
		}
		
		cart = cartRepo.save(cart);
		return cart;
	}
	
	// DESCRIPTION: After clicking on "Add to Cart" button, You can use this method
	// for "Delete from Cart" button to remove product WITHOUT need to go to cart
	@Transactional
	public Cart removeItemFromCart(Product product, String cid) {
		Cart cart = getCartByCid(cid);

		for(CartItem cartItem : cart.getCartItems()) {
			if(cartItem.getProduct().getId().equals(product.getId())) {
				
				cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
				cart.getCartItems().remove(cartItem);
				cart.setItemsNum(cart.getItemsNum() - 1);
				
				break;
			}
		}
		
		cart = cartRepo.save(cart);
		return cart;
	}
	
	
	@Transactional
	public Cart updateItemOfCart(CartItem item, String cid) {
		Cart cart = getCartByCid(cid);

		for (CartItem cartItem : cart.getCartItems()){
			if(cartItem.getProduct().getId().equals(item.getProduct().getId())) {
				
				cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
				cartItemMapper.mapEntityToEntity(cartItem, item);
				cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice());
				cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
				
				break;
			}
		}
		cart = cartRepo.save(cart);
		
		return cart;
	}
	
	@Transactional
	public boolean updateMyCart(Cart newcart, String cid) {
		Cart oldCart = getCartByCid(cid);
		if(oldCart==null) {
			log.info(" old cart not exist to update it");
			return false;
		}
		if(newcart==null) return false;

		oldCart.setTotalPrice(newcart.getTotalPrice());
		
		for(CartItem item: newcart.getCartItems()) {					
	        Optional<CartItem> oldItem = oldCart.getCartItems().stream()
            .filter(i -> i.getId().equals(item.getId()))
            .findFirst();

	        oldItem.get().setPrice(item.getPrice());
	        oldItem.get().setQuantity(item.getQuantity());
	        oldItem.get().setWeight(item.getWeight());
		}
		
		
		cartRepo.save(oldCart);
		return true;
	}
	
	@Transactional
	public void cleanCart(String cid) {
		Cart cart = getCartByCid(cid);
		cart.getCartItems().clear();
		cart.setItemsNum(0);
		cart.setTotalPrice(0.0);
		cartRepo.save(cart);
	}
	// s m h d(w) m d(m)
	@Scheduled(cron = "0 0 0 * * *") // every day at 12:00 AM
	@Transactional
	public void removeExpiredCarts() {
		LocalDateTime now = LocalDateTime.now();
		cartRepo.deleteExpiredCartsItems(now);
		cartRepo.deleteExpiredCarts(now);
		log.info("EXPIRED CARTS CLEARED SUCCESSFULLY!");
	}

}
