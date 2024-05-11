package com.matjarna.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matjarna.LoginService;
import com.matjarna.UniqueResponse;
import com.matjarna.UniqueValues;
import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.category.CategoryDescriptionDto;
import com.matjarna.dto.category.CategoryDto;
import com.matjarna.dto.category.CategoryHierarchyDto;
import com.matjarna.exception.ServiceException;
import com.matjarna.model.category.Category;
import com.matjarna.service.image.ImageService;

import jakarta.servlet.ServletException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class CategoriesTests {

	private static List<CategoryDto> categories = new ArrayList<>();

	private String token;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ImageService imageServiceMock;

	@Autowired
	private LoginService loginService;

	public CategoriesTests() {
	}

	@BeforeAll
	public void beforeAll() throws Exception {
		token = loginService.login("admin@matjarna.com", "Matjarna123");
	}

	static Stream<Arguments> categoryDtoProvider() {
		return Stream.of(
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("clothes1", "clothes title1"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("clothes2", "clothes title2"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("clothes", "clothes title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("shirts", "shirts title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("music", "music title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("singers", "singers title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("kotob", "kotob title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("dafater", "dafater title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("snacks", "snacks title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("food", "food title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("desserts", "desserts title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("cakes", "cakes title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("stakes", "stakes title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("chicken", "chicken title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("drinks", "drinks title"),
						true),
				Arguments.of(
						CategoryHelperMethods.createValidCategoryDtoOneDescription("cold drinks", "cold drinks title"),
						true),
				Arguments.of(
						CategoryHelperMethods.createValidCategoryDtoOneDescription("Hot drinks", "Hot drinks title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("ice cream", "ice cream title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("bottels", "bottels title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("glass", "glass title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("GLASSES", "GLASSES title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("games", "games title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("shawerma", "shawerma title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("AC", "AC title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("goex", "geox title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("Casamoda", "casamoda title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("bugatti", "bugatti title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("venti", "venti title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("pavarotti", "pavarotti title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("di porto", "di porto title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("Canali", "Canali title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("Reda", "Reda title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoOneDescription("Fynch Hatton",
						"Fynch Hatton title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("Jupyter", "Jupyter title"),
						true),
				Arguments.of(
						CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("Redelmare", "Redelmare title"),
						true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("Woolen world",
						"Woolen World title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("Atesh", "Atesh title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("Paul and Shark",
						"Paul And Shark title"), true),
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoTwoDecsriptions("S4", "S4 title"), true),
				Arguments.of(CategoryHelperMethods.createCategoryDtoWithMissingCode(), false),
				Arguments.of(CategoryHelperMethods.createCategoryDtoWithMissingActive(), false),
				Arguments.of(CategoryHelperMethods.createCategoryDtoWithMissingImage(), false),
				Arguments.of(CategoryHelperMethods.createCategoryDtoWithMissingDescriptionTitle(), false),
				Arguments.of(CategoryHelperMethods.createCategoryDtoWithNegativeSortOrder(), false));
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("categoryDtoProvider")
	void testCreateCategory(CategoryDto categoryDto, boolean expectSuccess) throws Exception {
		mockMvc.perform(post("/api/private/category/create").header("Authorization", "Bearer " + token)

				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					if (expectSuccess) {
						assertEquals(String.format("Test failed on category code = %s and sortOrder = %d",
								categoryDto.getCode(), categoryDto.getSortOrder()), 201, status);
						CategoryDto categoryReturned = objectMapper.readValue(result.getResponse().getContentAsString(),
								CategoryDto.class);
						categories.add(categoryReturned);
					} else {
						assertEquals(String.format("Test failed on category code = %s and sortOrder = %d",
								categoryDto.getCode(), categoryDto.getSortOrder()), 400, status);
					}
				});
	}

	static Stream<Arguments> databaseValidationDataSource() {
		return Stream.of(Arguments.of(CategoryHelperMethods.createDuplicateCategoryDto()),
				Arguments.of(CategoryHelperMethods.createDuplicateCategoryDtoWithChangeInCase()));
	}

	@Order(2)
	@MethodSource("databaseValidationDataSource")
	@ParameterizedTest
	public void databaseValidation(CategoryDto categoryDto) {

		Exception exception = assertThrows(
				String.format("Database failed to validate category with code = %s", categoryDto.getCode()),
				ServletException.class, () -> {
					testCreateCategory(categoryDto, false);
				});

		assertTrue("The cause is not DataIntegrityViolationException as expected.",
				exception.getCause() instanceof DataIntegrityViolationException);
	}

	public static Stream<Arguments> checkUniquenessDataProvider() {
		return Stream.of(
				// Editing an existing category
				Arguments.of(new UniqueValues(1L, "Leathers"), true),
				Arguments.of(new UniqueValues(1L, "Shirts"), false),

				// Adding new category
				Arguments.of(new UniqueValues(null, "clothes"), false),
				Arguments.of(new UniqueValues(null, "leathers"), true),

				// Trying a non-existing category ID
				Arguments.of(new UniqueValues(1L, "Clothes"), false),
				Arguments.of(new UniqueValues(900L, "leathers"), true),
				Arguments.of(new UniqueValues(900L, "shirts"), false));
	}

	@Order(3)
	@ParameterizedTest
	@MethodSource("checkUniquenessDataProvider")
	public void validateCheckUniqueness(UniqueValues test, boolean expected) throws Exception {

		MockHttpServletRequestBuilder requestBuilder = get("/api/private/category/unique")
				.param("code", test.getUniqueValue()).header("Authorization", "Bearer " + token);
		if (test.getId() != null) {
			requestBuilder.param("id", String.valueOf(test.getId()));
		}
		mockMvc.perform(requestBuilder).andExpect(result -> {

			UniqueResponse response = objectMapper.readValue(result.getResponse().getContentAsString(),
					UniqueResponse.class);
			assertEquals(
					String.format("Test failed on category code = %s and id = %d", test.getUniqueValue(), test.getId()),
					expected, response.isUnique());
		});
	}

	static Stream<Arguments> createCategoryWithValidSubCategoryDataProvider() {
		return Stream.of(
				Arguments.of(CategoryHelperMethods.createValidCategoryDtoWithParentId("tables", "tables title", 1L)));
	}

	@Order(4)
	@ParameterizedTest
	@MethodSource("createCategoryWithValidSubCategoryDataProvider")
	public void createCategoryWithValidSubCategory(CategoryDto categoryDto) throws JsonProcessingException, Exception {
		mockMvc.perform(post("/api/private/category/create").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					assertEquals(String.format("Test failed on category code = %s and sortOrder = %d",
							categoryDto.getCode(), categoryDto.getSortOrder()), 201, status);
					CategoryDto categoryReturned = objectMapper.readValue(result.getResponse().getContentAsString(),
							CategoryDto.class);
					categories.add(categoryReturned);
					assertEquals("Category Parent ID was not added correctly", categoryDto.getParentId(),
							categoryReturned.getParentId());
				});
	}

	static Stream<Arguments> createCategoryWithInvalidParentIdDataProvider() {
		return Stream.of(Arguments
				.of(CategoryHelperMethods.createValidCategoryDtoWithParentId("headphones", "headphones title", 8000L)));
	}

	@Order(5)
	@ParameterizedTest
	@MethodSource("createCategoryWithInvalidParentIdDataProvider")
	public void createCategoryWithInvalidParentId(CategoryDto categoryDto) throws JsonProcessingException, Exception {
		mockMvc.perform(post("/api/private/category/create").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)))
				.andExpect(result -> {

					assertEquals("The status is not 400 as expected!", 400, result.getResponse().getStatus());
				});
	}

	static Stream<Arguments> updateCategoryProvider() {
		return Stream.of(
				// Valid category ID, valid data
				Arguments.of(1L,
						CategoryHelperMethods.updateValidCategoryDto(categories.get(0), "clothes1",
								"clothes new title"),
						200),
				Arguments.of(4L,
						CategoryHelperMethods.updateValidCategoryDto(categories.get(3), "vapes", "vapes title"), 200),
				Arguments.of(2L,
						CategoryHelperMethods.updateValidCategoryDtoTwoDescriptions(categories.get(1), "shirts",
								"shirts title"),
						200),

				// Invalid ID or invalid data
				Arguments.of(3L, CategoryHelperMethods.createCategoryDtoWithMissingCode(), 400),
				Arguments.of(999L, null, 400), Arguments.of(-5L, null, 400),
				Arguments.of(null,
						CategoryHelperMethods.createValidCategoryDtoOneDescription("buildings", "buildings title"),
						404));
	}

	@Order(11)
	@ParameterizedTest
	@MethodSource("updateCategoryProvider")
	void testUpdateCategory(Long categoryId, CategoryDto updatedCategoryDto, int expectSuccess) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = put("/api/private/category/update/{id}", categoryId)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedCategoryDto));

		mockMvc.perform(requestBuilder).andExpect(result -> {
			int status = result.getResponse().getStatus();

			assertEquals("Status code mismatch", expectSuccess, status);

			if (status == 200) {
				String content = result.getResponse().getContentAsString();
				CategoryDto responseDto = objectMapper.readValue(content, CategoryDto.class);
				assertEquals("Code mismatch", updatedCategoryDto.getCode(), responseDto.getCode());
				assertEquals("Sort order mismatch", updatedCategoryDto.getSortOrder(), responseDto.getSortOrder());
				assertEquals("Image mismatch", updatedCategoryDto.getImage(), responseDto.getImage());

				// Test Description size and updated values
				assertEquals("Description list size should be correct", updatedCategoryDto.getDescriptions().size(),
						responseDto.getDescriptions().size());
				List<CategoryDescriptionDto> descriptions = new ArrayList<>(updatedCategoryDto.getDescriptions());
				descriptions.removeAll(responseDto.getDescriptions());
				assertEquals(String.format("The descriptions are not the same for the category id = %d",
						responseDto.getId()), 0, descriptions.size());
			}
		});
	}

	static Stream<Arguments> getCategoriesDataSource() {
		return Stream.of(
				// Small size (Size = 2) with two different pages and two different languages
				Arguments.of(0, 2, "en"), Arguments.of(1, 2, "en"), Arguments.of(0, 2, "ar"), Arguments.of(1, 2, "ar"),

				// Size bigger than the total number
				Arguments.of(0, 50, "en"),

				// Empty page
				Arguments.of(1, 55, "en"),

				// Null arguments
				Arguments.of(null, 5, null), Arguments.of(1, null, null));
	}

	@Order(21)
	@MethodSource("getCategoriesDataSource")
	@ParameterizedTest
	public void getCategoriesTest(Integer page, Integer size, String language) throws Exception {

		MockHttpServletRequestBuilder requestBuilder = get("/api/category/");
		if (page != null) {
			requestBuilder.param("page", String.valueOf(page));
		}
		if (size != null) {
			requestBuilder.param("size", String.valueOf(size));
		}
		if (language != null) {
			requestBuilder.param("language", language);
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {
			// Status check
			int status = result.getResponse().getStatus();
			assertEquals(String.format("Test failed on page = %d and size = %d with a status = %d", page, size, status),
					status, 200);

			// Total number check
			@SuppressWarnings("unchecked")
			EntityDtoList<CategoryDto, Category> categoriesList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);
			long totalNumberToCheck = categoriesList.getTotalNumber();
			long totalNumberGiven = CategoryHelperMethods.totalNumberCalculator(categories, language, null, null, null);
			assertEquals("The categories list totalNumber is not correct!", totalNumberGiven, totalNumberToCheck);

			// Size returned check
			int pageToCheck = (page == null) ? 0 : page;
			int sizeToCheck = (size == null) ? 10 : size;
			long currentPageSize = 0;
			if (totalNumberToCheck > sizeToCheck * pageToCheck) {
				currentPageSize = Math.min(totalNumberToCheck - pageToCheck * sizeToCheck, sizeToCheck);
			}
			assertEquals(
					String.format("Test failed with expectedCurrentPageSize = %d and found the actual size = %d",
							currentPageSize, categoriesList.getResults().size()),
					currentPageSize, categoriesList.getResults().size());
		});
	}

	static Stream<Arguments> categorySortCheckerDataSource() {
		return Stream.of(Arguments.of("d.title,asc"), Arguments.of("sortOrder,asc"), Arguments.of("code,asc"),
				Arguments.of(""));
	}

	@Order(22)
	@MethodSource("categorySortCheckerDataSource")
	@ParameterizedTest
	public void categorySortChecker(String sort) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/category/");
		if (!sort.isEmpty()) {
			requestBuilder.param("sort", sort);
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {
			// Total number check
			@SuppressWarnings("unchecked")
			EntityDtoList<CategoryDto, Category> entityDtoList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);

			List<CategoryDto> categoryDtoList = objectMapper.convertValue(entityDtoList.getResults(),
					new TypeReference<List<CategoryDto>>() {
					});

			String[] tokens = !sort.isBlank() ? sort.split(",") : new String[] { "sortOrder", "desc" };
			String field = tokens[0];
			String direction = tokens[1];
			List<CategoryDto> categoriesToSort = new ArrayList<>(categoryDtoList);
			Comparator<CategoryDto> comparator = null;

			switch (field) {
			case "sortOrder":
				comparator = Comparator.comparingInt(CategoryDto::getSortOrder);
				break;
			case "code":
				comparator = Comparator.comparing(CategoryDto::getCode);
				break;
			case "d.title":
				comparator = Comparator.comparing(
						dto -> dto.getDescriptions().isEmpty() ? "" : dto.getDescriptions().get(0).getTitle());
				break;
			default:
				throw new IllegalArgumentException("Unsupported sort field: " + field);
			}
			if ("desc".equalsIgnoreCase(direction)) {
				comparator = comparator.reversed();
			}
			categoriesToSort.sort(comparator);

			categoriesSortingAssertions(categoriesToSort, categoryDtoList, comparator);
		});

	}

	private void categoriesSortingAssertions(List<CategoryDto> categoriesListJavaSorted,
			List<CategoryDto> originalCategoryDtoList, Comparator<CategoryDto> comparator) {

		for (int i = 0; i < categoriesListJavaSorted.size(); ++i) {
			CategoryDto current = categoriesListJavaSorted.get(i);
			CategoryDto original = originalCategoryDtoList.get(i);
			assertTrue("Categories not sorted correctly", comparator.compare(current, original) == 0);
		}
	}

	static Stream<Arguments> filteringDataSource() {
		return Stream.of(
				// Null search term
				Arguments.of(true, null), Arguments.of(false, null),

				// different search term values with different active values
				Arguments.of(true, "Lea"), Arguments.of(false, "S"), Arguments.of(null, "S"),

				// Null request
				Arguments.of(null, null));
	}

	@Order(23)
	@MethodSource("filteringDataSource")
	@ParameterizedTest
	public void filteringTest(Boolean active, String searchTerm) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/category/");
		if (active != null) {
			requestBuilder.param("active", String.valueOf(active));
		}
		if (searchTerm != null) {
			requestBuilder.param("searchTerm", searchTerm);
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {

			@SuppressWarnings("unchecked")
			EntityDtoList<CategoryDto, Category> entityDtoList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);

			long totalNumberToCheck = entityDtoList.getTotalNumber();
			long totalNumber = CategoryHelperMethods.totalNumberCalculator(categories, null, null, searchTerm, active);
			assertEquals("The number of filtered categories is not correct!", totalNumber, totalNumberToCheck);
		});
	}

	static Stream<Arguments> categoryIdProvider() {
		return Stream.of(
				// Valid category IDs with descriptions
				Arguments.of(1L, categories.get(0), true), Arguments.of(2L, categories.get(1), true),

				// Non-existing category ID
				Arguments.of(999L, null, false), Arguments.of(-5L, null, false));
	}

	@Order(24)
	@ParameterizedTest
	@MethodSource("categoryIdProvider")
	void testGetCategoryById(Long categoryId, CategoryDto categoryDto, boolean expectSuccess) throws Exception {
		MvcResult result = mockMvc.perform(get("/api/category/{id}", categoryId)).andReturn();
		int status = result.getResponse().getStatus();
		if (expectSuccess) {
			assertEquals(String.format("Test failed on category id = %d ", categoryId), 200, status);
			if (categoryDto != null) {
				String content = result.getResponse().getContentAsString();
				CategoryDto responseDto = objectMapper.readValue(content, CategoryDto.class);

				assertEquals("Description list size should be correct", categoryDto.getDescriptions().size(),
						responseDto.getDescriptions().size());
			}
		} else {
			assertEquals(String.format("Test failed on category id = %d ", categoryId), 404, status);
		}
	}

	static Stream<Arguments> filterByParentIdDataProvider() {
		return Stream.of(Arguments.of(1), Arguments.of(3));
	}

	@Order(25)
	@ParameterizedTest
	@MethodSource("filterByParentIdDataProvider")
	void filterByParentId(long parentId) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/category/");
		requestBuilder.param("parentId", String.valueOf(parentId));

		mockMvc.perform(requestBuilder).andExpect(result -> {

			@SuppressWarnings("unchecked")
			EntityDtoList<CategoryDto, Category> entityDtoList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);

			long totalNumberToCheck = entityDtoList.getTotalNumber();
			long totalNumber = 0;
			for (CategoryDto category : categories) {
				if (category.getParentId() != null && category.getParentId() == parentId) {
					++totalNumber;
				}
			}
			assertEquals("The number of filtered categories is not correct!", totalNumber, totalNumberToCheck);
		});
	}

	@Order(26)
	@Test
	public void getCategoryHierarchy() throws JsonProcessingException, Exception {
		mockMvc.perform(get("/api/category/hierarchy")).andExpect(result -> {
			int status = result.getResponse().getStatus();
			assertEquals("Test getCategoryHierarchy Failed on status check on category code = %s and id = %d", 200,
					status);
			List<CategoryHierarchyDto> returned = objectMapper.readValue(result.getResponse().getContentAsString(),
					new TypeReference<List<CategoryHierarchyDto>>() {
					});
			List<CategoryHierarchyDto> expected = CategoryHelperMethods.getHierarchy(categories);
			assertEquals("getCategoryHierarchy test failed!", true,
					CategoryHelperMethods.hierarchyChecker(expected, returned));
		});
	}

	static Stream<Arguments> deleteCategoryProvider() {
		return Stream.of(
				// Non-existing category ID
				Arguments.of(999L), Arguments.of(-5L));
	}

	@Order(31)
	@ParameterizedTest
	@MethodSource("deleteCategoryProvider")
	void testDeleteCategoryFailure(Long categoryId) throws Exception {
		Exception exception = assertThrows(String.format("Delete test failed on category id = %d", categoryId),
				ServletException.class, () -> {
					mockMvc.perform(delete("/api/private/category/{id}", categoryId).header(HttpHeaders.AUTHORIZATION,
							"Bearer " + token));
				});
		assertTrue("The cause is not ServiceException as expected.", exception.getCause() instanceof ServiceException);
	}

	static Stream<Arguments> deleteParentCategory() {
		return Stream.of(Arguments.of(1L));
	}

	@Order(32)
	@ParameterizedTest
	@MethodSource("deleteParentCategory")
	void testDeleteParentCategory(Long categoryId) throws Exception {
		Exception exception = assertThrows(String.format("Delete test failed on category id = %d", categoryId),
				ServletException.class, () -> {
					mockMvc.perform(delete("/api/private/category/{id}", categoryId).header("Authorization",
							"Bearer " + token));
				});
		assertTrue("The cause is not ServiceException as expected.",
				exception.getCause() instanceof DataIntegrityViolationException);
	}

	@Order(33)
	@Test
	void testDeleteCategorySuccess() throws Exception {
		for (int i = categories.size() - 1; i >= 0; --i) {
			CategoryDto categoryDto = categories.get(i);
			mockMvc.perform(delete("/api/private/category/{id}", categoryDto.getId()).header(HttpHeaders.AUTHORIZATION,
					"Bearer " + token)).andExpect(result -> {
						int status = result.getResponse().getStatus();

						assertEquals(String.format("Delete test failed on category id = %d", categoryDto.getId()), 200,
								status);
					});
		}
		mockMvc.perform(get("/api/category/")).andExpect(r -> {
			@SuppressWarnings("unchecked")
			EntityDtoList<Category, CategoryDto> entityDtoList = objectMapper
					.readValue(r.getResponse().getContentAsString(), EntityDtoList.class);
			long totalNumber = entityDtoList.getTotalNumber();

			assertEquals("Test failed not all categories deleted", 0, totalNumber);
		});
	}
}
