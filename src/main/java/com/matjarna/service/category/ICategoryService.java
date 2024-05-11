package com.matjarna.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matjarna.dto.category.CategoryFilters;
import com.matjarna.model.category.Category;
import com.matjarna.model.language.Language;
import com.matjarna.model.subModel.CategoryHierarchy;

public interface ICategoryService {

	Category saveCategory(Category category);

	Category getByCode(String code);

	Optional<Category> getCategoryById(Long id);

	void deleteCategory(Long id);

	Page<Category> getCategories(Pageable pageable, Language language, CategoryFilters search);

	long getNumberOfChildren(long parentId);

	List<CategoryHierarchy> getCategoryHierarchy();

}
