package com.global.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDto{

    private Long id;
    
    private OrderDto order;
    
    private ProductDto product;
    
    private Long quantity;
    
    private Double weight;
    
    private Double price;
	
}
