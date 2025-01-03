package com.global.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.global.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offers")

@Setter
@Getter
public class Offer extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String offerType; // E.g., "percentage", "fixedPrice"
	
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "offer")
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
	
    private Double discountValue;
	
    private LocalDate startDate;
    
    private LocalDate endDate;
}


