package com.global.projection;

import java.util.Set;

import com.global.entity.OrderItem;

public interface OrderDetails extends AuditingProjection{
	
	Long getId();
	
	String getUserFirstName();
	
	String getUserLastName();
	
	String getCompanyName();
	
	String getDeliveryAddress();
	
	String getPhoneNumber();
	
	String getEmailAddress();
	
	String getOtherNotes();
	
	Double getTotalPrice();
	
	String getPaymentType();
	
	String getStatus();
	
	Set<OrderItem> getOrderItems();

}
