package com.matjarna.service.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matjarna.dto.product.ProductFilters;
import com.matjarna.model.country.Country;
import com.matjarna.model.language.Language;
import com.matjarna.model.product.Product;

public interface IProductService {

	Product saveProduct(Product product);

	Product getByCode(String code);

	long getNumberOfProducts(long id);

	Optional<Product> getById(long id);

	Page<Product> getProducts(Pageable pageable, Language language, ProductFilters search, Country country);

	Optional<Product> getProductById(Long id);

	void deleteProduct(Long id);

}
