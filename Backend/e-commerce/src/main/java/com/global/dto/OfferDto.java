package com.global.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.global.base.AuditorDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OfferDto extends AuditorDto{
	
	private Long id;
	
	private String offerType;
	
    private BigDecimal discountValue;
	
    private LocalDate startDate;
    
    private LocalDate endDate;
	
}
