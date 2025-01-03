package com.global.dto;

import com.global.entity.Cart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemDto{

    private Long id;

    private Cart cart;

    private ProductDto product;
    
    private OrderDto order;
    
    private Long quantity;
    
    private Double weight;
    
    private Double price;
	
}
