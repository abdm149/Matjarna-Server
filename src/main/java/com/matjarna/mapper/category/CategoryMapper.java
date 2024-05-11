package com.matjarna.mapper.category;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.matjarna.dto.category.CategoryDto;
import com.matjarna.exception.ValidationException;
import com.matjarna.model.category.Category;
import com.matjarna.service.category.ICategoryService;
import com.matjarna.service.product.IProductService;

@Mapper(componentModel = "spring", uses = CategoryDescriptionMapper.class)
public abstract class CategoryMapper {

	@Autowired
	public ICategoryService categoryService;

	@Autowired
	public IProductService productService;

	@Mappings({ @Mapping(source = "parent", target = "parentId", qualifiedByName = "mapParentToParentId"),
			@Mapping(source = "id", target = "numberOfChildren", qualifiedByName = "getNumberOfChildren"),
			@Mapping(source = "id", target = "numberOfProducts", qualifiedByName = "getNumberOfProducts") })
	public abstract CategoryDto toCategoryDto(Category category);

	@Mapping(source = "parentId", target = "parent", qualifiedByName = "mapParentIdToParent")
	public abstract Category toCategory(CategoryDto categoryDto);

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
			throw new ValidationException("Invalid Parent!");
		}
		return optional.get();
	}

	@Named("getNumberOfChildren")
	long getNumberOfChildren(long id) {
		return categoryService.getNumberOfChildren(id);
	}

	@Named("getNumberOfProducts")
	long getNumberOfProducts(long id) {
		return productService.getNumberOfProducts(id);
	}

}