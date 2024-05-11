package com.matjarna.controller.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.EntityUnique;
import com.matjarna.dto.product.ProductDto;
import com.matjarna.dto.product.ProductFilters;
import com.matjarna.facade.product.IProductFacade;
import com.matjarna.model.country.Country;
import com.matjarna.model.language.Language;
import com.matjarna.model.product.Product;

import jakarta.validation.Valid;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "api")
public class ProductApi {

	@Autowired
	IProductFacade productFacade;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/product/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDto create(@RequestBody @Valid ProductDto productDto) {
		return productFacade.saveProduct(productDto);
	}

	@GetMapping("/private/product/unique")
	public ResponseEntity<EntityUnique> checkCodeUniqueness(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "code", required = true) String code) {
		EntityUnique entityUnique = new EntityUnique();
		entityUnique.setUnique(productFacade.checkUniqueness(id, code));
		return ResponseEntity.status(200).body(entityUnique);
	}

	@GetMapping(value = "/private/product/")
	public ResponseEntity<EntityDtoList<ProductDto, Product>> getProducts(
			@PageableDefault(sort = "sortOrder", direction = Sort.Direction.DESC) Pageable pageable,
			@ApiIgnore Language language, ProductFilters search) {
		EntityDtoList<ProductDto, Product> products = productFacade.getProducts(pageable, language, search, null);
		return ResponseEntity.status(200).body(products);
	}

	@GetMapping(value = "product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
		Optional<ProductDto> productDto = productFacade.getProductById(id);
		return productDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "private/product/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		productFacade.deleteProduct(id);
		return ResponseEntity.status(200).build();
	}

	@PutMapping(value = "private/product/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable Long id) {
		productDto.setId(id);
		ProductDto updatedProdyctDto = productFacade.saveProduct(productDto);
		return ResponseEntity.ok(updatedProdyctDto);
	}

	@GetMapping(value = "/product/")
	public ResponseEntity<EntityDtoList<ProductDto, Product>> getProductPublic(
			@PageableDefault(sort = "sortOrder", direction = Sort.Direction.DESC) Pageable pageable,
			@ApiIgnore Language language, ProductFilters search, @ApiIgnore Country country) {
		search.setActive(true);
		EntityDtoList<ProductDto, Product> products = productFacade.getProducts(pageable, language, search, country);
		return ResponseEntity.status(200).body(products);
	}
	
}
