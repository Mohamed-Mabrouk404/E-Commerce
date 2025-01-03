package com.global.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String>{
	
	@Query("Select p from Product p where p.name = :name")
	public Product findByName(String name);
	
	@Query("Select p from Product p where p.name LIKE %:name% OR :name LIKE CONCAT('%', p.name, '%')")
	public List<Product> search(String name);
	
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
	
	
//	--------------------------------------------
//	Analytics part
	
	
}
