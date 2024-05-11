package com.matjarna.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matjarna.dto.category.CategoryDescriptionDto;
import com.matjarna.dto.category.CategoryDto;
import com.matjarna.dto.category.CategoryHierarchyDto;

public class CategoryHelperMethods {

	public static CategoryDto createValidCategoryDtoOneDescription(String code, String title) {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(title, "English Description", "en"));

		CategoryDto categoryDto = createCategory(code, 3, true, "example-image-url", null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createValidCategoryDtoTwoDecsriptions(String code, String title) {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(title + "-en", "English Description", "en"));
		descriptions.add(createDescription(title + "-ar", "Arabic Description", "ar"));

		CategoryDto categoryDto = createCategory(code, 3, true, "example-image-url", null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createValidCategoryDtoWithParentId(String code, String title, long parentId) {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(title, "English Description", "en"));

		CategoryDto categoryDto = createCategory(code, 3, true, "example-image-url", parentId, descriptions);
		return categoryDto;
	}

	public static CategoryDto createCategoryDtoWithMissingCode() {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		CategoryDto categoryDto = createCategory(null, 3, false, "example-image-url", null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createCategoryDtoWithNegativeSortOrder() {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		CategoryDto categoryDto = createCategory("TEST_CODE989898", -1, false, "example-image-url", null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createCategoryDtoWithMissingActive() {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		CategoryDto categoryDto = createCategory("code21313", 2, null, "example-image-url", null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createCategoryDtoWithMissingImage() {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		CategoryDto categoryDto = createCategory("code81313", 2, true, null, null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createCategoryDtoWithMissingDescriptionTitle() {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(null, "English Description", "en"));

		CategoryDto categoryDto = createCategory("code215397", 2, true, "example-image-url", null, descriptions);
		return categoryDto;
	}

	public static CategoryDto createDuplicateCategoryDto() {
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		CategoryDto categoryDto = createCategory("clothes", 3, true, "example-image-url", null, descriptions);

		return categoryDto;
	}

	public static CategoryDto createDuplicateCategoryDtoWithChangeInCase() {

		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		CategoryDto categoryDto = createCategory("Clothes", 3, true, "example-image-url", null, descriptions);

		return categoryDto;
	}

	public static CategoryDto updateValidCategoryDto(CategoryDto categoryDto, String code, String title) {
		categoryDto.setCode(code);

		// Add a description
		CategoryDescriptionDto descriptionDto = null;
		if (categoryDto.getDescriptions().size() == 0) {
			descriptionDto = new CategoryDescriptionDto();
			categoryDto.getDescriptions().add(descriptionDto);
		} else {
			descriptionDto = categoryDto.getDescriptions().get(0);
		}
		descriptionDto.setTitle(title);
		descriptionDto.setDescription("English Description");
		descriptionDto.setLanguage("en");

		return categoryDto;
	}

	public static CategoryDto updateValidCategoryDtoTwoDescriptions(CategoryDto categoryDto, String code,
			String title) {
		final String[] languages = { "en", "ar" };

		// Add a description
		for (int i = 0; i < languages.length; ++i) {
			boolean found = false;
			for (int j = 0; j < categoryDto.getDescriptions().size(); ++j) {
				CategoryDescriptionDto descriptionDtoToCheck = categoryDto.getDescriptions().get(j);
				if (descriptionDtoToCheck.getLanguage().equals(languages[i])) {
					descriptionDtoToCheck.setTitle(title + " 1 " + languages[i]);
					descriptionDtoToCheck.setDescription("This is the " + languages[i] + " of " + title);
					found = true;
				}
				if (!found) {
					CategoryDescriptionDto descriptionDto = new CategoryDescriptionDto();
					descriptionDto.setTitle(title + " 1 " + languages[i]);
					descriptionDto.setDescription("This is the " + languages[i] + " of " + title);
					descriptionDto.setLanguage("en");
					categoryDto.getDescriptions().add(descriptionDto);
				}
			}
		}

		return categoryDto;
	}

	private static CategoryDescriptionDto createDescription(String title, String description, String language) {
		CategoryDescriptionDto descriptionDto = new CategoryDescriptionDto();
		descriptionDto.setTitle(title);
		descriptionDto.setDescription(description);
		descriptionDto.setLanguage(language);
		return descriptionDto;
	}

	private static CategoryDto createCategory(String code, Integer sortOrder, Boolean active, String image,
			Long parentId, List<CategoryDescriptionDto> descriptions) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCode(code);
		categoryDto.setActive(active);
		categoryDto.setImage(image);
		categoryDto.setSortOrder(sortOrder);
		categoryDto.setParentId(parentId);
		categoryDto.setDescriptions(descriptions);
		return categoryDto;
	}

	public static long totalNumberCalculator(List<CategoryDto> categories, String language, Long parentId,
			String searchTerm, Boolean active) {

		long result = 0;
		for (CategoryDto category : categories) {
			boolean activeMatches = (active == null) || category.isActive() == active;
			boolean searchTermMatches = false;
			boolean parentMatches = (parentId == null && category.getParentId() == null)
					|| (parentId != null && parentId.equals(category.getParentId()));
			boolean languageMatches = language == null ? true : false;
			if (activeMatches && parentMatches) {
				if (searchTerm != null && !searchTerm.isEmpty()) {
					if (category.getCode() != null
							&& category.getCode().toLowerCase().contains(searchTerm.toLowerCase())) {
						searchTermMatches = true;
					} else if (!category.getDescriptions().isEmpty()) {
						for (CategoryDescriptionDto description : category.getDescriptions()) {
							if (description.getTitle() != null
									&& description.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
								searchTermMatches = true;
								break;
							}
						}
					}
				} else {
					// If search term is null
					searchTermMatches = true;
				}
			}
			if (searchTermMatches && language != null) {
				for (CategoryDescriptionDto description : category.getDescriptions()) {
					if (language.equals(description.getLanguage())) {
						languageMatches = true;
						break;
					}
				}
			}
			if (activeMatches && searchTermMatches && parentMatches && languageMatches) {
				++result;
			}
		}
		return result;
	}

	public static List<CategoryHierarchyDto> getHierarchy(List<CategoryDto> children) {

		Comparator<CategoryDto> comparator = Comparator.comparingInt(CategoryDto::getSortOrder);
		children.sort(comparator);

		Map<Long, CategoryHierarchyDto> result = new HashMap<>();
		CategoryHierarchyDto root = new CategoryHierarchyDto();
		result.put(null, root);

		for (CategoryDto category : children) {
			CategoryHierarchyDto parent;
			if (result.containsKey(category.getParentId())) {
				parent = result.get(category.getParentId());
			} else {
				parent = new CategoryHierarchyDto();
				parent.setId(category.getParentId());
				result.put(parent.getId(), parent);
			}

			CategoryHierarchyDto child = new CategoryHierarchyDto();
			child.setId(category.getId());
			child.setCode(category.getCode());
			parent.getChildren().add(child);
			result.put(category.getId(), child);
		}
		return root.getChildren();
	}

	public static boolean hierarchyChecker(List<CategoryHierarchyDto> categoriesExpected,
			List<CategoryHierarchyDto> categoriesReturned) {
		for (int i = 0; i < categoriesExpected.size(); ++i) {
			if (categoriesExpected.get(i).getId() != categoriesReturned.get(i).getId()) {
				return false;
			}
		}
		return true;
	}

}
