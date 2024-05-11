package com.matjarna.mapper.product;

import java.util.Comparator;
import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.matjarna.dto.product.ProductDto;
import com.matjarna.dto.product.ProductImageDto;
import com.matjarna.exception.ValidationException;
import com.matjarna.mapper.price.PriceMapper;
import com.matjarna.model.category.Category;
import com.matjarna.model.product.Product;
import com.matjarna.service.category.ICategoryService;

@Mapper(componentModel = "spring", uses = { ProductDescriptionMapper.class, ProductImageMapper.class,
		PriceMapper.class })
public abstract class ProductMapper {
	@Autowired
	ICategoryService categoryService;

	@Mappings({ @Mapping(source = "parentId", target = "parent", qualifiedByName = "mapParentIdToParent"),
			@Mapping(source = "mainImage", target = "image") })
	public abstract Product toProduct(ProductDto productDto);

	@Mappings({ @Mapping(source = "parent", target = "parentId", qualifiedByName = "mapParentToParentId"),
			@Mapping(source = "image", target = "mainImage") })
	public abstract ProductDto toDto(Product product);

	@Named("mapParentToParentId")
	Long mapParentToParentId(Category parent) {
		return parent != null ? parent.getId() : null;
	}

	@Named("mapParentIdToParent")
	Category mapParentIdToParent(Long parentId) {
		if (parentId == null) {
			return null;
		}
		Optional<Category> optional = categoryService.getCategoryById(parentId);
		if (optional.isEmpty()) {
			throw new ValidationException("Invalid Parent ID!");
		}

		long numberOfSubCategories = categoryService.getNumberOfChildren(parentId);
		if (numberOfSubCategories > 0) {
			throw new ValidationException("This category contains sub-categories!");
		}
		return optional.get();
	}

	@AfterMapping
	protected void sortImages(Product product, @MappingTarget ProductDto productDto) {
		productDto.getSubImages().sort(Comparator.comparing(ProductImageDto::getSortOrder).reversed());
	}

}
