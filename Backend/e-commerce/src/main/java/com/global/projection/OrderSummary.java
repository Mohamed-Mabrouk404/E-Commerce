package com.global.projection;

public interface OrderSummary extends AuditingProjection{
	
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

}
