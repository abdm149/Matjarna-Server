package com.matjarna.controller.category;

import java.util.List;
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
import com.matjarna.dto.category.CategoryDto;
import com.matjarna.dto.category.CategoryFilters;
import com.matjarna.dto.category.CategoryHierarchyDto;
import com.matjarna.facade.category.ICategoryFacade;
import com.matjarna.model.category.Category;
import com.matjarna.model.language.Language;

import jakarta.validation.Valid;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "api")
public class CategoryApi {

	@Autowired
	private ICategoryFacade categoryFacade;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "private/category/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryDto create(@RequestBody @Valid CategoryDto categoryDto) {

		return categoryFacade.saveCategory(categoryDto);
	}

	@GetMapping("private/category/unique")
	public ResponseEntity<EntityUnique> checkCodeUniqueness(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "code", required = true) String code) {
		EntityUnique entityUnique = new EntityUnique();
		entityUnique.setUnique(categoryFacade.checkUniqueness(id, code));
		return ResponseEntity.status(200).body(entityUnique);
	}

	@GetMapping(value = "category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
		Optional<CategoryDto> categoryDto = categoryFacade.getCategoryById(id);
		return categoryDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "private/category/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		categoryFacade.deleteCategory(id);
		return ResponseEntity.status(200).build();
	}

	@GetMapping(value = "category/")
	public ResponseEntity<EntityDtoList<CategoryDto, Category>> getCategories(
			@PageableDefault(sort = "sortOrder", direction = Sort.Direction.DESC) Pageable pageable,
			@ApiIgnore Language language, CategoryFilters search) {
		EntityDtoList<CategoryDto, Category> categories = categoryFacade.getCategories(pageable, language, search);
		return ResponseEntity.status(200).body(categories);
	}

	@PutMapping(value = "private/category/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto,
			@PathVariable Long id) {
		categoryDto.setId(id);
		CategoryDto updatedCategoryDto = categoryFacade.saveCategory(categoryDto);
		return ResponseEntity.ok(updatedCategoryDto);
	}

	@GetMapping(value = "category/hierarchy")
	public ResponseEntity<List<CategoryHierarchyDto>> getCategoryHierarchy() {
		List<CategoryHierarchyDto> categories = categoryFacade.getCategoryHierarchy();
		return ResponseEntity.status(200).body(categories);
	}
}