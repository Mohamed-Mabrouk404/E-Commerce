package com.global.dto;

import com.global.base.AuditorDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductWithCategoryDto extends AuditorDto{
	
	private String id;
	
	private String name;
	
	private String imageUrl;
	
	private Double originalPrice;
	
	private Double discountedPrice;
	
	private String description;
	
	private Double stockChange;
	
	private Double stockQuantity;
	
	private Double stockWeight;
	
	private Double minBuyWeight;
	
	private boolean buyWithWeight;
	
	private boolean hasDiscount;
	
	private CategoryDto category;
	
	private String brand;
	
	private Double rating;
    
    private Integer purchaseCount;
    
    private OfferDto offer;
	
}
