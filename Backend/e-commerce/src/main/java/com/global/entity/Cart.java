package com.global.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.global.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "carts")

@Setter
@Getter

@Log4j2
public class Cart extends BaseEntity{
	
	@Id
	private String cid; // cart id equals user id (uid)
	
	private Double totalPrice = 0.0;
	
	private int itemsNum = 0;
	
	private LocalDateTime expTime; // expiration time of the cart 
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<CartItem> cartItems = new HashSet<>();
		
	public void addItem(CartItem item) {
		cartItems.add(item);
		itemsNum++;
		item.setCart(this);
	}	
}



