package com.global.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.global.dto.ProductDto;
import com.global.dto.ProductWithCategoryDto;
import com.global.entity.Product;
import com.global.mapper.ProductMapper;
import com.global.mapper.ProductWithCategoryMapper;
import com.global.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
@Log4j2
public class ProductController {

	private final ProductService productService;

	private final ProductWithCategoryMapper productWithCategoryMapper;

	private final ProductMapper productMapper;

	@GetMapping("")
	public List<ProductWithCategoryDto> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "null") String sortBy,
			@RequestParam(defaultValue = "false") boolean isAsc) {

		Page<Product> products = productService.getProducts(page, size, sortBy, isAsc);
		return productWithCategoryMapper.mapToDto(products.getContent());
	}

	@GetMapping("/{id}")
	public Optional<ProductWithCategoryDto> findById(@PathVariable String id) {
		Optional<Product> product = productService.findById(id);
		ProductWithCategoryDto productDto = productWithCategoryMapper.mapToDto(product.get());
		return Optional.of(productDto);
	}

	@GetMapping("/search")
	public List<ProductDto> search(@RequestParam String name) {
		List<Product> lp = productService.search(name);
		log.info("lp size is " + lp.size());
		List<ProductDto> lpd = productMapper.mapToDto(lp);
		log.info("lpd size is " + lpd.size());
		return lpd;
	}

	@GetMapping("/name/{name}")
	public Product findByName(@PathVariable String name) {
		return productService.findByName(name);
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<ProductWithCategoryDto> insert(@RequestPart("product") ProductWithCategoryDto productDto,
			@RequestPart("img") MultipartFile productImg) throws IOException {

		Product product = productWithCategoryMapper.mapToEntity(productDto);

		product = productService.insert(product, productImg);

		return ResponseEntity.ok(productWithCategoryMapper.mapToDto(product));
	}

	@PutMapping()
	public ResponseEntity<ProductWithCategoryDto> update(@RequestPart("product") ProductWithCategoryDto productDto,
			@RequestPart("img") MultipartFile productImg) throws IOException {

		Product product = productWithCategoryMapper.mapToEntity(productDto);

		product = productService.update(product, productImg);

		return ResponseEntity.ok(productWithCategoryMapper.mapToDto(product));
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable String id) {
		productService.deleteById(id);
	}

}
