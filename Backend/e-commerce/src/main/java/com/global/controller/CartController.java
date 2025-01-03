package com.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.dto.CartDto;
import com.global.entity.Cart;
import com.global.entity.CartItem;
import com.global.entity.Product;
import com.global.mapper.CartMapper;
import com.global.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/carts")
@Log4j2
public class CartController {

	private final CartMapper cartMapper;

	private final CartService cartService;

	@GetMapping("/myCart")
	public ResponseEntity<?> getMyCart(@RequestHeader("uid") String cid) {
		Cart cart = cartService.getCartByCid(cid);
		return ResponseEntity.ok(cart);
	}

	@PutMapping("/updateMyCart")
	public ResponseEntity<?> updateMyCart(@RequestBody CartDto newCartDto, @RequestHeader("uid") String cid) {
		cartService.updateMyCart(cartMapper.mapToEntity(newCartDto), cid);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/cleanCart")
	public ResponseEntity<?> cleanMyCart(@RequestHeader("uid") String cid) {
		cartService.cleanCart(cid);
		return ResponseEntity.ok(null);
	}

	@PostMapping("/items/add")
	public ResponseEntity<?> AddItemToCart(@RequestBody CartItem cartItem, @RequestHeader("uid") String cid) {
		return cartService.addItemToCart(cartItem, cid);
	}

	@DeleteMapping("/items/removeById/{cartItemId}")
	public Cart removeItemFromCart(@PathVariable Long cartItemId, @RequestHeader("uid") String cid) {
		return cartService.removeItemFromCart(cartItemId, cid);
	}

	@DeleteMapping("/items/remove")
	public Cart removeItemFromCart(@RequestBody CartItem cartItem, @RequestHeader("uid") String cid) {
		return cartService.removeItemFromCart(cartItem, cid);
	}

	@DeleteMapping("/products/remove")
	public Cart removeItemFromCart(@RequestBody Product product, @RequestHeader("uid") String cid) {
		return cartService.removeItemFromCart(product, cid);
	}

	@PutMapping("/items/update")
	public Cart updateItemOfCart(@RequestBody CartItem cartItem, @RequestHeader("uid") String cid) {
		return cartService.updateItemOfCart(cartItem, cid);
	}

}
