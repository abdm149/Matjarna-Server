package com.matjarna.service.category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matjarna.dto.category.CategoryFilters;
import com.matjarna.exception.ServiceException;
import com.matjarna.model.category.Category;
import com.matjarna.model.category.CategoryDescription;
import com.matjarna.model.language.Language;
import com.matjarna.model.subModel.CategoryHierarchy;
import com.matjarna.repository.category.ICategoryRepository;
import com.matjarna.service.image.ImageService;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public long getNumberOfChildren(long parentId) {
		return categoryRepository.getNumberOfChildren(parentId);
	}

	@Override
	public Category saveCategory(Category category) {
		boolean update = category.getId() != null;
		category.setCode(category.getCode().toLowerCase());

		Set<CategoryDescription> descriptions = category.getDescriptions();
		for (CategoryDescription description : descriptions) {
			description.setCategory(category);
		}

		String oldImagePath = null;
		if (update) {
			Category oldCategory = categoryRepository.findById(category.getId()).get();
			if (!oldCategory.getImage().equals(category.getImage())) {
				oldImagePath = oldCategory.getImage();
			}
		}

		Category categoryToReturn = categoryRepository.save(category);
		if (oldImagePath != null) {
			imageService.deleteImage(oldImagePath);
		}
		return categoryToReturn;
	}

	@Override
	public Category getByCode(String code) {
		return categoryRepository.getByCode(code.toLowerCase());
	}

	@Override
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public void deleteCategory(Long id) {
		Optional<Category> categoryToDelete = categoryRepository.findById(id);
		if (!categoryToDelete.isPresent()) {
			throw new ServiceException(String.format("Category with id %d wasn't found", id));
		}
		categoryRepository.deleteById(id);
		imageService.deleteImage(categoryToDelete.get().getImage());
	}

	@Override
	public Page<Category> getCategories(Pageable pageable, Language language, CategoryFilters search) {
		Page<Category> page = categoryRepository.getCategories(pageable, language, search.isActive(),
				search.getSearchTerm(), search.getParentId());
		return page;
	}

	@Override
	public List<CategoryHierarchy> getCategoryHierarchy() {
		return categoryRepository.getAllCategories();
	}
}
