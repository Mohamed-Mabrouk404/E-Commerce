package com.global.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.global.dto.OfferDto;
import com.global.entity.Offer;

@Mapper(componentModel = "spring")
public interface OfferMapper {
	
	OfferDto mapToDto(Offer offer);
	List<OfferDto> mapToDto(List<Offer> offers);
	Set<OfferDto> mapToDto(Set<Offer> offers);
	
	
	@Mapping(target = "id" , ignore = true)
	Offer mapToEntity(OfferDto offerDto);
	List<Offer> mapToEntity(List<OfferDto> offerDto);
	Set<Offer> mapToEntity(Set<OfferDto> offerDto);
	
}
