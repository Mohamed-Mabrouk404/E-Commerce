package com.global.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.global.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "products")

@Setter
@Getter

@Log4j2
public class Product extends BaseEntity{

	@Id
	private String id;
	
	private String name;
	
	private String imageUrl;
	
	private Double originalPrice;
	private Double discountedPrice;
	
	private String description;
	
	@Transient
	private Double stockChange;
	
	private Long stockQuantity;
	private Double stockWeight;
	
	private Double minBuyWeight;

	private boolean buyWithWeight;
	
	private boolean hasDiscount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
//	@JsonBackReference
	@JsonIgnoreProperties({"products"})
	private Category category;
	
	
	private String brand;
	private Double rating;
    private Integer purchaseCount = 0;    
    
    
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "offerId")
    private Offer offer;
	
    
	public Product() {
		super();
		
		this.discountedPrice = this.originalPrice;
		this.stockQuantity = 0L;
		this.stockWeight = 0.0;
		this.minBuyWeight = 250.0;
		this.buyWithWeight = false;
		this.hasDiscount = false;
	}   
	
	@PrePersist
	public void setID() {
		this.id = UUID.randomUUID().toString();
	}
	
	public void increasePurchaseCount(){
		this.purchaseCount++;
	}
	
	public void decreasePurchaseCount(){
		this.purchaseCount--;
	}
	
	
	public void increaseStockQuantityBy(Long quantity){
		this.stockQuantity += quantity;
	}
	public void decreaseStockQuantityBy(Long quantity){
		this.stockQuantity -= quantity;
	}
	
	public void increaseStockWeightBy(Double weight){
		this.stockWeight += weight;
	}
	public void decreaseStockWeightBy(Double weight){
		this.stockWeight -= weight;
	}
	
}


