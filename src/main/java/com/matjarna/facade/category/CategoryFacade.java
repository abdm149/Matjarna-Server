package com.matjarna.facade.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.category.CategoryDto;
import com.matjarna.dto.category.CategoryFilters;
import com.matjarna.dto.category.CategoryHierarchyDto;
import com.matjarna.mapper.category.CategoryHierarchyMapper;
import com.matjarna.mapper.category.CategoryMapper;
import com.matjarna.model.category.Category;
import com.matjarna.model.language.Language;
import com.matjarna.model.subModel.CategoryHierarchy;
import com.matjarna.service.category.ICategoryService;

@Service
public class CategoryFacade implements ICategoryFacade {

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private CategoryHierarchyMapper categoryHierarchyMapper;

	@Override
	public CategoryDto saveCategory(CategoryDto categoryDto) {
		Category category = categoryMapper.toCategory(categoryDto);
		// Pass mapped Category to service layer
		Category createdCategory = categoryService.saveCategory(category);
		CategoryDto mappedCategory = categoryMapper.toCategoryDto(createdCategory);
		return mappedCategory;
	}

	@Override
	public boolean checkUniqueness(Long id, String code) {
		Category category = categoryService.getByCode(code);
		if (category == null || (id != null && category.getId() == id)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Optional<CategoryDto> getCategoryById(Long id) {
		Optional<Category> categoryOptional = categoryService.getCategoryById(id);
		Optional<CategoryDto> categoryDtoOptional = categoryOptional.map(categoryMapper::toCategoryDto);
		return categoryDtoOptional;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryService.deleteCategory(id);
	}

	@Override
	public EntityDtoList<CategoryDto, Category> getCategories(Pageable pageable, Language language,
			CategoryFilters search) {
		Page<Category> page = categoryService.getCategories(pageable, language, search);
		EntityDtoList<CategoryDto, Category> entityDtoList = new EntityDtoList<>(page, categoryMapper::toCategoryDto);
		return entityDtoList;
	}

	@Override
	public List<CategoryHierarchyDto> getCategoryHierarchy() {
		List<CategoryHierarchy> categories = categoryService.getCategoryHierarchy();
		List<CategoryHierarchyDto> categoriesDto = categoryHierarchyMapper.toDto(categories);
		Map<Long, CategoryHierarchyDto> categoryMap = categoriesDto.stream()
				.collect(Collectors.toMap(CategoryHierarchyDto::getId, Function.identity()));

		List<CategoryHierarchyDto> categoriesToReturn = new ArrayList<>();
		for (CategoryHierarchyDto category : categoriesDto) {
			CategoryHierarchyDto parent;
			if (category.getParentId() != null) {
				if (categoryMap.containsKey(category.getParentId())) {
					parent = categoryMap.get(category.getParentId());
					parent.getChildren().add(category);
				} else {
					// TODO: Change this print using a logging library
					System.err.println("Parent Not Found!");
				}
			} else {
				categoriesToReturn.add(category);
			}
		}
		return categoriesToReturn;
	}

}
