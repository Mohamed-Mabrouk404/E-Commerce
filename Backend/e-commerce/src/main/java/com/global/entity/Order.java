package com.global.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.global.base.AuditorEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")

@Setter
@Getter

//@AllArgsConstructor
public class Order extends AuditorEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userFirstName;
	
	private String userLastName;
	
	private String companyName;
	
	private String deliveryAddress;
	
	private String cityName; // new
	
	private String governorate; // new
	
	private String phoneNumber;
	
	private String phoneNumber2; // new
	
//	private String emailAddress;
	
	private String otherNotes;
	
	private int itemsNum;
	
	private Double totalPrice;
	
	private String paymentType;
	
	private String status;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<OrderItem> orderItems = new HashSet<>();
	
	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public Order() {
		super();
		this.status = "pending"; 
	}
	
}


