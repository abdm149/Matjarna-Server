package com.matjarna.product;

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

import org.junit.jupiter.api.AfterAll;
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
import com.matjarna.category.CategoryHelperMethods;
import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.category.CategoryDto;
import com.matjarna.dto.price.PriceDto;
import com.matjarna.dto.product.ProductDescriptionDto;
import com.matjarna.dto.product.ProductDto;
import com.matjarna.dto.product.ProductImageDto;
import com.matjarna.exception.ServiceException;
import com.matjarna.model.product.Product;
import com.matjarna.service.image.ImageService;

import jakarta.servlet.ServletException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductsTests {

	private static List<ProductDto> products = new ArrayList<>();

	private static List<CategoryDto> categories = new ArrayList<>();

	private String token;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LoginService loginService;

	@MockBean
	private ImageService imageServiceMock;

	public ProductsTests() {
	}

	@BeforeAll
	public void beforeAll() throws JsonProcessingException, Exception {

		token = loginService.login("admin@matjarna.com", "Matjarna123");

		categories.add(CategoryHelperMethods.createValidCategoryDtoOneDescription("clothes1", "clothes title1"));
		categories.add(CategoryHelperMethods.createValidCategoryDtoOneDescription("clothes2", "clothes title2"));
		categories.add(CategoryHelperMethods.createValidCategoryDtoWithParentId("clothes3", "clothes title1", 2));
		categories.add(CategoryHelperMethods.createValidCategoryDtoWithParentId("clothes4", "clothes title1", 3));
		for (CategoryDto categoryDto : categories) {
			mockMvc.perform(post("/api/private/category/create").header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)))
					.andExpect(result -> {

						CategoryDto category = objectMapper.readValue(result.getResponse().getContentAsString(),
								CategoryDto.class);
						categoryDto.setId(category.getId());

					});
		}

	}

	@AfterAll
	public void afterAll() throws Exception {
		for (CategoryDto categoryDto : categories) {
			mockMvc.perform(delete("/api/private/category/{id}", categoryDto.getId()));
		}
	}

	static Stream<Arguments> productDtoProvider() {
		return Stream.of(
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("clothes1", "clothes title1",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("clothes2", "clothes title2",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("clothes", "clothes title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("shirts", "shirts title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("music", "music title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("singers", "singers title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("kotob", "kotob title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("dafater", "dafater title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("snacks", "snacks title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("food", "food title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("desserts", "desserts title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("cakes", "cakes title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("stakes", "stakes title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("chicken", "chicken title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("drinks", "drinks title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("cold drinks",
						"cold drinks title", categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("Hot drinks", "Hot drinks title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("ice cream", "ice cream title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("bottels", "bottels title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("glass", "glass title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("GLASSES", "GLASSES title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("games", "games title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("shawerma", "shawerma title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("AC", "AC title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("goex", "geox title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("Casamoda", "casamoda title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("bugatti", "bugatti title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("venti", "venti title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("pavarotti", "pavarotti title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("di porto", "di porto title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("Canali", "Canali title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("Reda", "Reda title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoOneDescription("Fynch Hatton",
						"Fynch Hatton title", categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("Jupyter", "Jupyter title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("Redelmare", "Redelmare title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("Woolen world",
						"Woolen World title", categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("Atesh", "Atesh title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("Paul and Shark",
						"Paul And Shark title", categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createValidProductDtoTwoDecsriptions("S4", "S4 title",
						categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createProductDtoWithNoEndDate(categories.get(0).getId()), true),
				Arguments.of(ProductHelperMethods.createProductDtoWithMissingCode(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithMissingActive(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithMissingImage(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithMissingDescriptionTitle(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithNegativeSortOrder(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithMissingParentId(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithNullSubImages(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithInvalidDateFormat(), false),
				Arguments.of(ProductHelperMethods.createProductDtoWithInvalidEndDate(), false));
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("productDtoProvider")
	void testCreateProduct(ProductDto productDto, boolean expectSuccess) throws Exception {

		mockMvc.perform(post("/api/private/product/create").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productDto)))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					if (expectSuccess) {
						assertEquals(String.format("Test failed on product code = %s and sortOrder = %d",
								productDto.getCode(), productDto.getSortOrder()), 201, status);
						ProductDto productReturned = objectMapper.readValue(result.getResponse().getContentAsString(),
								ProductDto.class);
						products.add(productReturned);
					} else {
						assertEquals(String.format("Test failed on product code = %s and sortOrder = %d",
								productDto.getCode(), productDto.getSortOrder()), 400, status);
					}
				});
	}

	static Stream<Arguments> databaseValidationDataSource() {
		return Stream.of(Arguments.of(ProductHelperMethods.createDuplicateProductDto(categories.get(0).getId())),
				Arguments
						.of(ProductHelperMethods.createDuplicateProductDtoWithChangeInCase(categories.get(0).getId())));
	}

	@Order(2)
	@MethodSource("databaseValidationDataSource")
	@ParameterizedTest
	public void databaseValidation(ProductDto productDto) {

		Exception exception = assertThrows(
				String.format("Database failed to validate product with code = %s", productDto.getCode()),
				ServletException.class, () -> {
					testCreateProduct(productDto, false);
				});
		assertTrue("The cause is not DataIntegrityViolationException as expected.",
				exception.getCause() instanceof DataIntegrityViolationException);
	}

	static Stream<Arguments> createProductWithInvalidParentIdDataProvider() {
		return Stream.of(Arguments
				.of(ProductHelperMethods.createValidProductDtoOneDescription("headphones", "headphones title", 8000L)));
	}

	@Order(3)
	@ParameterizedTest
	@MethodSource("createProductWithInvalidParentIdDataProvider")
	public void createProductWithInvalidParentId(ProductDto productDto) throws JsonProcessingException, Exception {

		mockMvc.perform(post("/api/private/product/create").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productDto)))
				.andExpect(result -> {

					assertEquals("The status is not 400 as expected!", 400, result.getResponse().getStatus());
				});
	}

	public Stream<Arguments> checkUniquenessDataProvider() {
		return Stream.of(
				// Editing an existing product
				Arguments.of(new UniqueValues(products.get(0).getId(), "Clothes1"), true),
				Arguments.of(new UniqueValues(products.get(0).getId(), "Leathers"), true),
				Arguments.of(new UniqueValues(products.get(0).getId(), "Shirts"), false),

				// Adding new product
				Arguments.of(new UniqueValues(null, "clothes"), false),
				Arguments.of(new UniqueValues(null, "leathers"), true),

				// Trying a non-existing product ID
				Arguments.of(new UniqueValues(900L, "leathers"), true),
				Arguments.of(new UniqueValues(900L, "shirts"), false));
	}

	@Order(4)
	@ParameterizedTest
	@MethodSource("checkUniquenessDataProvider")
	public void validateCheckUniqueness(UniqueValues test, boolean expected) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/private/product/unique")
				.param("code", test.getUniqueValue()).header("Authorization", "Bearer " + token);
		if (test.getId() != null) {
			requestBuilder.param("id", String.valueOf(test.getId()));
		}
		mockMvc.perform(requestBuilder).andExpect(result -> {
			UniqueResponse response = objectMapper.readValue(result.getResponse().getContentAsString(),
					UniqueResponse.class);
			assertEquals(
					String.format("Test failed on product code = %s and id = %d", test.getUniqueValue(), test.getId()),
					expected, response.isUnique());
		});
	}

	static Stream<Arguments> updateProductProvider() {
		return Stream.of(
				// Valid category ID, valid data
				Arguments.of(1L,
						ProductHelperMethods.updateValidProductDto(products.get(0), "clothes1", "clothes new title"),
						200),
				Arguments.of(4L, ProductHelperMethods.updateValidProductDto(products.get(3), "vapes", "vapes title"),
						200),
				Arguments.of(2L,
						ProductHelperMethods.updateValidCategoryDtoTwoDescriptions(products.get(1), "shirts",
								"shirts title"),
						200),

				// Invalid ID or invalid data
				Arguments.of(3L, ProductHelperMethods.createProductDtoWithMissingCode(), 400),
				Arguments.of(999L, null, 400), Arguments.of(-5L, null, 400));
	}

	@Order(11)
	@ParameterizedTest
	@MethodSource("updateProductProvider")
	public void testUpdateProduct(Long productId, ProductDto updatedProductDto, int expectSuccess) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = put("/api/private/product/update/{id}", productId)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedProductDto));

		mockMvc.perform(requestBuilder).andExpect(result -> {
			int status = result.getResponse().getStatus();

			assertEquals("Status code mismatch", expectSuccess, status);

			if (status == 200) {
				String content = result.getResponse().getContentAsString();
				ProductDto responseDto = objectMapper.readValue(content, ProductDto.class);
				assertEquals("Code mismatch", updatedProductDto.getCode(), responseDto.getCode());
				assertEquals("Sort order mismatch", updatedProductDto.getSortOrder(), responseDto.getSortOrder());
				assertEquals("Image mismatch", updatedProductDto.getMainImage(), responseDto.getMainImage());

				// Compare descriptions, prices, subImages
				assertEquals("Description list size should be correct", updatedProductDto.getDescriptions().size(),
						responseDto.getDescriptions().size());
				List<ProductDescriptionDto> descriptions = new ArrayList<>(updatedProductDto.getDescriptions());
				descriptions.removeAll(responseDto.getDescriptions());
				assertEquals(
						String.format("The descriptions are not the same for the product id = %d", responseDto.getId()),
						0, descriptions.size());

				assertEquals("Prices list size should be correct", updatedProductDto.getPrices().size(),
						responseDto.getPrices().size());
				List<PriceDto> prices = new ArrayList<>(updatedProductDto.getPrices());
				prices.removeAll(responseDto.getPrices());
				assertEquals(String.format("The prices are not the same for the product id = %d", responseDto.getId()),
						0, prices.size());

				assertEquals("Images list size should be correct", updatedProductDto.getSubImages().size(),
						responseDto.getSubImages().size());
				List<ProductImageDto> images = new ArrayList<>(updatedProductDto.getSubImages());
				images.removeAll(responseDto.getSubImages());
				assertEquals(
						String.format("The subImages are not the same for the product id = %d", responseDto.getId()), 0,
						images.size());

				int index = (int) (productId - 1);
				products.set(index, responseDto);
			}
		});
	}

	static Stream<Arguments> getProductsDataSource() {
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
	@MethodSource("getProductsDataSource")
	@ParameterizedTest
	public void getProductsTest(Integer page, Integer size, String language) throws Exception {

		MockHttpServletRequestBuilder requestBuilder = get("/api/private/product/").header("Authorization",
				"Bearer " + token);
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
			EntityDtoList<ProductDto, Product> productsList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);
			long totalNumberToCheck = productsList.getTotalNumber();
			long totalNumberGiven = ProductHelperMethods.totalNumberCalculator(products, language, null, null, null,
					null, null);
			assertEquals("The products list totalNumber is not correct!", totalNumberGiven, totalNumberToCheck);

			// Size returned check
			int pageToCheck = (page == null) ? 0 : page;
			int sizeToCheck = (size == null) ? 10 : size;
			long currentPageSize = 0;
			if (totalNumberToCheck > sizeToCheck * pageToCheck) {
				currentPageSize = Math.min(totalNumberToCheck - pageToCheck * sizeToCheck, sizeToCheck);
			}
			assertEquals(
					String.format("Test failed with expectedCurrentPageSize = %d and found the actual size = %d",
							currentPageSize, productsList.getResults().size()),
					currentPageSize, productsList.getResults().size());
		});
	}

	static Stream<Arguments> productSortCheckerDataSource() {
		return Stream.of(Arguments.of("d.title,asc"), Arguments.of("p.sortOrder,desc"), Arguments.of("p.code,asc"),
				Arguments.of(""), Arguments.of("c.code,asc,pr.price,asc"), Arguments.of("c.code,desc,pr.price,desc"),
				Arguments.of("c.code,asc,pr.price,desc"));
	}

	@Order(22)
	@MethodSource("productSortCheckerDataSource")
	@ParameterizedTest
	public void productSortChecker(String sort) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/private/product/").header("Authorization",
				"Bearer " + token);
		String[] tokens = !sort.isBlank() ? sort.split(",") : new String[] { "p.sortOrder", "desc" };
		if (!sort.isEmpty()) {
			if (tokens.length == 4) {
				requestBuilder.param("sort", tokens[0] + "," + tokens[1]);
				requestBuilder.param("sort", tokens[2] + "," + tokens[3]);
			} else {
				requestBuilder.param("sort", sort);
			}
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {
			// Total number check
			@SuppressWarnings("unchecked")
			EntityDtoList<ProductDto, Product> entityDtoList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);

			List<ProductDto> productDtoList = objectMapper.convertValue(entityDtoList.getResults(),
					new TypeReference<List<ProductDto>>() {
					});

			List<ProductDto> productsToSort = new ArrayList<>(productDtoList);
			Comparator<ProductDto> comparator = null;
			String field = tokens[0];
			String direction = tokens[1];

			switch (field) {
			case "p.sortOrder":
				comparator = Comparator.comparingInt(ProductDto::getSortOrder);
				break;
			case "p.code":
				comparator = Comparator.comparing(ProductDto::getCode);
				break;
			case "d.title":
				comparator = Comparator.comparing(
						dto -> dto.getDescriptions().isEmpty() ? "" : dto.getDescriptions().get(0).getTitle());
				break;
			case "c.code":
				comparator = ProductHelperMethods.getCountryAndPriceComparator(sort);
				direction = tokens[2];
				break;
			default:
				throw new IllegalArgumentException("Unsupported sort field: " + field);
			}
			if ("desc".equalsIgnoreCase(direction)) {
				comparator = comparator.reversed();
			}
			productsToSort.sort(comparator);

			productsSortingAssertions(productsToSort, productDtoList, comparator);
		});

	}

	private void productsSortingAssertions(List<ProductDto> productsListJavaSorted,
			List<ProductDto> originalProductDtoList, Comparator<ProductDto> comparator) {

		for (int i = 0; i < productsListJavaSorted.size(); ++i) {
			ProductDto current = productsListJavaSorted.get(i);
			ProductDto original = originalProductDtoList.get(i);
			assertTrue("Products not sorted correctly", comparator.compare(current, original) == 0);
		}
	}

	static Stream<Arguments> filteringDataSource() {
		return Stream.of(
				// Null search term
				Arguments.of(true, true, null), Arguments.of(false, false, null),

				// Different search term values with different active values
				Arguments.of(true, true, "Lea"), Arguments.of(false, true, "S"), Arguments.of(null, null, "S"),

				// Null request
				Arguments.of(null, null, null));
	}

	@Order(24)
	@MethodSource("filteringDataSource")
	@ParameterizedTest
	public void filteringTest(Boolean active, Boolean discounted, String searchTerm) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/private/product/").header("Authorization",
				"Bearer " + token);
		if (active != null) {
			requestBuilder.param("active", String.valueOf(active));
		}
		if (searchTerm != null) {
			requestBuilder.param("searchTerm", searchTerm);
		}
		if (discounted != null) {
			requestBuilder.param("discounted", String.valueOf(discounted));
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {

			@SuppressWarnings("unchecked")
			EntityDtoList<ProductDto, Product> entityDtoList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);

			long totalNumberToCheck = entityDtoList.getTotalNumber();
			long totalNumber = ProductHelperMethods.totalNumberCalculator(products, null, null, searchTerm, active,
					discounted, null);
			assertEquals("The number of filtered products is not correct!", totalNumber, totalNumberToCheck);
		});
	}

	static Stream<Arguments> productIdProvider() {
		return Stream.of(
				// Valid product IDs
				Arguments.of(1L, products.get(0), true), Arguments.of(2L, products.get(1), true),

				// Non-existing product ID
				Arguments.of(999L, null, false), Arguments.of(-5L, null, false));
	}

	@Order(25)
	@ParameterizedTest
	@MethodSource("productIdProvider")
	public void testGetProductById(Long productId, ProductDto productDto, boolean expectSuccess) throws Exception {
		MvcResult result = mockMvc.perform(get("/api/product/{id}", productId)).andReturn();
		int status = result.getResponse().getStatus();
		if (expectSuccess) {
			assertEquals(String.format("Test failed on product id = %d ", productId), 200, status);
			if (productDto != null) {
				String content = result.getResponse().getContentAsString();
				ProductDto responseDto = objectMapper.readValue(content, ProductDto.class);

				assertEquals("Description list size should be correct", productDto.getDescriptions().size(),
						responseDto.getDescriptions().size());
				assertEquals("Price list size should be correct", productDto.getPrices().size(),
						responseDto.getPrices().size());
				assertEquals("Sub-Images list size should be correct", productDto.getSubImages().size(),
						responseDto.getSubImages().size());
			}
		} else {
			assertEquals(String.format("Test failed on product id = %d ", productId), 404, status);
		}
	}

	static Stream<Arguments> getProductPublicDataSource() {
		return Stream.of(
				// Small size (Size = 2) with two different pages and two different languages
				// with different countries
				Arguments.of(0, 2, "en", "PS"), Arguments.of(0, 2, "en", "il"), Arguments.of(1, 2, "en", "us"),
				Arguments.of(0, 2, "ar", null), Arguments.of(1, 2, "ar", null),

				// Size bigger than the total number
				Arguments.of(0, 50, "en", "us"),

				// Empty page
				Arguments.of(1, 55, "en", "US"),

				// Null arguments
				Arguments.of(null, 5, null, "jod"), Arguments.of(1, null, null, null));
	}

	@Order(26)
	@MethodSource("getProductPublicDataSource")
	@ParameterizedTest
	public void getProductPublicTest(Integer page, Integer size, String language, String country) throws Exception {

		MockHttpServletRequestBuilder requestBuilder = get("/api/product/");
		if (page != null) {
			requestBuilder.param("page", String.valueOf(page));
		}
		if (size != null) {
			requestBuilder.param("size", String.valueOf(size));
		}
		if (language != null) {
			requestBuilder.param("language", language);
		}
		if (country != null) {
			requestBuilder.param("country", country);
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {
			// Status check
			int status = result.getResponse().getStatus();
			assertEquals(String.format("Test failed on page = %d and size = %d with a status = %d", page, size, status),
					status, 200);
			String countryCode;
			if (country != null) {
				switch (country.toUpperCase()) {
				case "PS":
				case "US":
					countryCode = country;
					break;
				case "IL":
					countryCode = "PS";
					break;
				default:
					countryCode = "US";
					break;
				}
			} else {
				countryCode = "US";
			}

			// Total number check
			@SuppressWarnings("unchecked")
			EntityDtoList<ProductDto, Product> productsList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);
			long totalNumberToCheck = productsList.getTotalNumber();
			long totalNumberGiven = ProductHelperMethods.totalNumberCalculator(products, language, null, null, true,
					null, countryCode);
			assertEquals("The products list totalNumber is not correct!", totalNumberGiven, totalNumberToCheck);

			// Size returned check
			int pageToCheck = (page == null) ? 0 : page;
			int sizeToCheck = (size == null) ? 10 : size;
			long currentPageSize = 0;
			if (totalNumberToCheck > sizeToCheck * pageToCheck) {
				currentPageSize = Math.min(totalNumberToCheck - pageToCheck * sizeToCheck, sizeToCheck);
			}
			assertEquals(
					String.format("Test failed with expectedCurrentPageSize = %d and found the actual size = %d",
							currentPageSize, productsList.getResults().size()),
					currentPageSize, productsList.getResults().size());
			List<ProductDto> productsDtoList = (List<ProductDto>) objectMapper.convertValue(productsList.getResults(),
					new TypeReference<List<ProductDto>>() {
					});
			for (ProductDto productDto : productsDtoList) {
				for (PriceDto price : productDto.getPrices()) {
					assertEquals("The price country does not match the input country",
							price.getCountryCode().toLowerCase(), countryCode.toLowerCase());
				}
			}
		});
	}

	static Stream<Arguments> deleteProductProvider() {
		return Stream.of(
				// Non-existing product ID
				Arguments.of(999L), Arguments.of(-5L));
	}

	@Order(31)
	@ParameterizedTest
	@MethodSource("deleteProductProvider")
	public void testDeleteProductFailure(Long productId) throws Exception {

		Exception exception = assertThrows(String.format("Delete test failed on product id = %d", productId),
				ServletException.class, () -> {
					mockMvc.perform(delete("/api/private/product/{id}", productId).header(HttpHeaders.AUTHORIZATION,
							"Bearer " + token));
				});
		assertTrue("The cause is not ServiceException as expected.", exception.getCause() instanceof ServiceException);
	}

	@Order(32)
	@Test
	public void testDeleteProductSuccess() throws Exception {
		for (int i = products.size() - 1; i >= 0; --i) {
			ProductDto productDto = products.get(i);
			mockMvc.perform(delete("/api/private/product/{id}", productDto.getId()).header(HttpHeaders.AUTHORIZATION,
					"Bearer " + token)).andExpect(result -> {
						int status = result.getResponse().getStatus();

						assertEquals(String.format("Delete test failed on product id = %d", productDto.getId()), 200,
								status);
					});
		}
		mockMvc.perform(get("/api/private/product/").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(r -> {
					@SuppressWarnings("unchecked")
					EntityDtoList<Product, ProductDto> entityDtoList = objectMapper
							.readValue(r.getResponse().getContentAsString(), EntityDtoList.class);
					long totalNumber = entityDtoList.getTotalNumber();

					assertEquals("Test failed not all products deleted", 0, totalNumber);
				});
	}

}
