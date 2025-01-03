package com.global.dto;

import java.util.HashSet;
import java.util.Set;

import com.global.base.AuditorDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDto extends AuditorDto{

	private String SessionId;
	
	private Double totalPrice;
	
	private int itemsNum;
	
	private Set<CartItemDto> cartItems = new HashSet<>();
	
}
