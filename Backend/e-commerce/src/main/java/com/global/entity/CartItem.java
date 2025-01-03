package com.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "cart_items")

@Setter
@Getter

@Log4j2
public class CartItem{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Long quantity = 1L;
    
    private Double weight;
    
    private Double price;
    
    
    @PrePersist
    public void increaseCartPrice() {
    	this.setPrice(quantity * this.product.getDiscountedPrice());
    	this.cart.setTotalPrice(this.cart.getTotalPrice() + this.price);
    	this.setWeight(this.product.getMinBuyWeight());
    }
 
}


