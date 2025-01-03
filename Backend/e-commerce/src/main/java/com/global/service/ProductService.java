package com.global.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.global.entity.Category;
import com.global.entity.Offer;
import com.global.entity.Product;
import com.global.mapper.ProductMapper;
import com.global.repository.CategoryRepo;
import com.global.repository.OfferRepo;
import com.global.repository.ProductRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

	private final ProductRepo productRepo;
	private final CategoryRepo categoryRepo;
	private final OfferRepo offerRepo;
	
	private final ProductMapper productMapper;
	
	private FileUploadService fileUploadService;
	
    @Autowired
    public void FileUploadService(@Lazy FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
	
	public Page<Product> getProducts(int page, int size, String sortBy, boolean isAsc) {
		Pageable pageable;
		if(!sortBy.equals("null")) {
			pageable = PageRequest.of(page, size, 
					Sort.by(isAsc ? Direction.ASC : Direction.DESC, sortBy));
		}else {
			pageable = PageRequest.of(page, size);
		}
		return productRepo.findAll(pageable);
	}
	
	public Page<Product> getCategoryProducts(Long categoryId, int page, int size, String sortBy, boolean isAsc) {
		Pageable pageable;
		if(!sortBy.equals("null")) {
			pageable = PageRequest.of(page, size, 
					Sort.by(isAsc ? Direction.ASC : Direction.DESC, sortBy));
		}else {
			pageable = PageRequest.of(page, size);
		}
		return productRepo.findByCategoryId(categoryId, pageable);
	}
	
	public Optional<Product> findById(String id){
		return productRepo.findById(id);
	}
	
	public List<Product> search(String name) {
	    if (name == null || name.isEmpty()) return Collections.emptyList();
		return productRepo.search(name);
	}
	
	public Product findByName(String name) {
		return productRepo.findByName(name);
	}
	
	@Transactional
	public Product insert(Product newproduct, MultipartFile productImg) throws IOException{
		updateStock(newproduct);

		LocalDate today = LocalDate.now();
		if(newproduct.getOffer()!= null && !today.isBefore(newproduct.getOffer().getStartDate()))
			calcDiscountPrice(newproduct);
		
		Category category = categoryRepo.findByName(newproduct.getCategory().getName());
		newproduct.setCategory(category);
		category.getProducts().add(newproduct);
		category.setProductsNum(category.getProductsNum() + 1);
		
		newproduct = productRepo.save(newproduct);
		
		updateProductImg(newproduct, productImg);
		
		categoryRepo.save(category);
		
		return newproduct;
	}
	
	@Transactional
	public Product update(Product newProduct, MultipartFile productImg) throws IOException{
		Product currentProduct = productRepo.findById(newProduct.getId()).get();

		//log.info("currentProduct: " + objectMapper.writeValueAsString(currentProduct));
		
		Category newCategory = categoryRepo.findByName(newProduct.getCategory().getName());
		Category oldCategory = categoryRepo.findByName(currentProduct.getCategory().getName());
		
		oldCategory.getProducts().remove(currentProduct);
		oldCategory.setProductsNum(oldCategory.getProductsNum() - 1);
		
		currentProduct.setCategory(newCategory);
		newCategory.getProducts().add(currentProduct);
		newCategory.setProductsNum(newCategory.getProductsNum() + 1);

		productMapper.mapEntityToEntity(currentProduct, newProduct);
		
		updateStock(currentProduct);
		calcDiscountPrice(currentProduct);
		
		categoryRepo.save(oldCategory);
		categoryRepo.save(newCategory);

		currentProduct = productRepo.save(currentProduct);
		
		updateProductImg(currentProduct, productImg);
		
		return currentProduct;
	}
	
	@Transactional
	public void updateProductImg(Product product, MultipartFile productImg) throws IOException {
		if (productImg == null) return; // if product image not set
		
		// if product image still the same
		if(product.getImageUrl() != null && product.getImageUrl().contains(productImg.getOriginalFilename())) 
			return; 

		// if product image changed or set
		fileUploadService.deleteFile(product.getImageUrl());
		String[] fileDetails = fileUploadService.uploadFile(product.getId(), "product", fileUploadService.convertMultipartFileToFile(productImg));
		fileUploadService.updateFilePath(fileDetails[0],fileDetails[1],fileDetails[2]);
	}
	
	@Transactional
	public void deleteById(String id){
		Product product = productRepo.findById(id).get();
		Category productCategory = categoryRepo.findById(product.getCategory().getId()).get();
		
		product.setCategory(null);
		productCategory.getProducts().remove(product);
		productCategory.setProductsNum(productCategory.getProductsNum() - 1);
		
		fileUploadService.deleteFile(product.getImageUrl());
		
		categoryRepo.save(productCategory);
	}
	
    public void updateStock(Product product) {
    	if(product.isBuyWithWeight()) {
    		product.setStockWeight(product.getStockWeight() + product.getStockChange());
    		product.setStockQuantity(null);
    	}else {
    		product.setStockQuantity((long) (product.getStockQuantity() + product.getStockChange()));
    		product.setStockWeight(null);
    	}
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // runs daily
    public void updateDiscountedPrices() {
    	LocalDate today = LocalDate.now();
    	
    	setDiscountedPrices(today);
    	resetDiscountedPrices(today);
    }
    
    public void setDiscountedPrices(LocalDate today) { // for just starting offers 
    	List<Offer> activeOffers = offerRepo.findActiveOffers(today);
    	
    	for (Offer offer : activeOffers) {
    		Product product = offer.getProduct();
    		calcDiscountPrice(product);
    		productRepo.save(product);
    	}
    }
    public void resetDiscountedPrices(LocalDate today) { // for just ended offers
    	List<Offer> expiredOffers = offerRepo.findExpiredOffers(today);
    	
    	for (Offer offer : expiredOffers) {
    		Product product = offer.getProduct();
    		product.setOffer(null);
    		calcDiscountPrice(product);
    		productRepo.save(product);
    	}
    }
    
    public void calcDiscountPrice(Product product) {
    	String offerType;
    	if(product.getOffer() == null) offerType = "no offer";
    	else offerType = product.getOffer().getOfferType();
    	Double productOriginalPrice = product.getOriginalPrice();
		switch (offerType) {
			case "fixedPrice": {
				product.setDiscountedPrice(productOriginalPrice - product.getOffer().getDiscountValue());
				break;
			}
			case "percentage":{
				product.setDiscountedPrice(Math.ceil(productOriginalPrice - (productOriginalPrice * (product.getOffer().getDiscountValue() / 100))));
				break;
			}
			default:
				product.setDiscountedPrice(productOriginalPrice);
		}    		
    }

}
