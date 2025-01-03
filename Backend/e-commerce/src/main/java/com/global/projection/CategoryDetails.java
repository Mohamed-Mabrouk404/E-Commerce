package com.global.projection;

import java.util.Set;

import com.global.entity.Product;

public interface CategoryDetails {
	
	Long getId();
	
	String getName();
	
	Long getProductsNum();
	
	Set<Product> getProducts();
}
