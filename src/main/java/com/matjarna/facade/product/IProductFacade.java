package com.matjarna.facade.product;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.product.ProductDto;
import com.matjarna.dto.product.ProductFilters;
import com.matjarna.model.country.Country;
import com.matjarna.model.language.Language;
import com.matjarna.model.product.Product;

public interface IProductFacade {

	ProductDto saveProduct(ProductDto productDto);

	boolean checkUniqueness(Long id, String code);

	EntityDtoList<ProductDto, Product> getProducts(Pageable pageable, Language language, ProductFilters search,
			Country country);

	Optional<ProductDto> getProductById(Long id);

	void deleteProduct(Long id);

}
