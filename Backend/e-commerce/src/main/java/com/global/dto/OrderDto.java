package com.global.dto;

import java.util.Set;

import com.global.base.AuditorDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto extends AuditorDto{

	private Long id;
	
	private String userFirstName;
	
	private String userLastName;
	
	private String companyName;
	
	private String deliveryAddress;
		
	private String cityName; 
	
	private String governorate; 
	
	private String phoneNumber;
	
	private String phoneNumber2; 
	
	private String otherNotes;
	
	private int itemsNum;
	
	private Double totalPrice;
	
	private String paymentType;
	
	private String status;
	
	private Set<OrderItemDto> orderItems; 
	
}
