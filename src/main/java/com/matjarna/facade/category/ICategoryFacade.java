package com.matjarna.facade.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.category.CategoryDto;
import com.matjarna.dto.category.CategoryFilters;
import com.matjarna.dto.category.CategoryHierarchyDto;
import com.matjarna.model.category.Category;
import com.matjarna.model.language.Language;

public interface ICategoryFacade {

	CategoryDto saveCategory(CategoryDto categoryDto);

	boolean checkUniqueness(Long id, String code);

	Optional<CategoryDto> getCategoryById(Long id);

	void deleteCategory(Long id);

	EntityDtoList<CategoryDto, Category> getCategories(Pageable pageable, Language language, CategoryFilters search);

	List<CategoryHierarchyDto> getCategoryHierarchy();
}
