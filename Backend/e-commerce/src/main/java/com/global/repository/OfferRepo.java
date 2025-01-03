package com.global.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.entity.Offer;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long>{
	
	// return active offers
	@Query("SELECT offer FROM Offer offer WHERE :date >= offer.startDate AND :date <= offer.endDate")
	List<Offer> findActiveOffers(LocalDate date);
	
	// return ended offers
	@Query("SELECT offer FROM Offer offer WHERE :date > offer.endDate")
	List<Offer> findExpiredOffers(LocalDate date);
	
}
