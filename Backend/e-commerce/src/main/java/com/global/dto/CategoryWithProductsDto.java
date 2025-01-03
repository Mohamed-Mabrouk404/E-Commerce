package com.global.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryWithProductsDto{
	
	private Long id;
	
	private String name;
	
	private Long productsNum;
	
	private Set<ProductDto> products;
	
}
