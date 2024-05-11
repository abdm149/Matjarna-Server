package com.matjarna.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.matjarna.dto.price.DiscountDto;
import com.matjarna.dto.price.PriceDto;
import com.matjarna.dto.product.ProductDescriptionDto;
import com.matjarna.dto.product.ProductDto;
import com.matjarna.dto.product.ProductImageDto;

public class ProductHelperMethods {

	public static ProductDto createValidProductDtoOneDescription(String code, String title, long parentId) {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(title, "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1 " + code, 1));
		images.add(createImage("image2 " + code, 1));

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", null));
		prices.add(createPrice(20.5, "US", "USD", createDiscount(12.5, "1/1/2029", null)));

		ProductDto productDto = createProduct(code, 3, true, "example-image-url", parentId, descriptions, images,
				prices);
		return productDto;
	}

	public static ProductDto createValidProductDtoTwoDecsriptions(String code, String title, long parentId) {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(title + "-en", "English Description", "en"));
		descriptions.add(createDescription(title + "-ar", "Arabic Description", "ar"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1 " + code, 1));
		images.add(createImage("someImageTitle " + title, 3));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct(code, 3, true, "example-image-url", parentId, descriptions, images,
				prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithMissingParentId() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("title for missing parent desc", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("code for missing parent", 3, true, "example-image-url", null,
				descriptions, images, prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithMissingCode() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct(null, 3, false, "example-image-url", 1L, descriptions, images, prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithNullSubImages() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("a code", 3, false, "example-image-url", 1L, descriptions, null, prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithNegativeSortOrder() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("TEST_CODE989898", -1, false, "example-image-url", 1L, descriptions,
				images, prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithMissingActive() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("code21313", 2, null, "example-image-url", 1L, descriptions, images,
				prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithMissingImage() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("code81313", 2, true, null, 1L, descriptions, images, prices);
		return productDto;
	}

	public static ProductDto createProductDtoWithMissingDescriptionTitle() {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription(null, "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("code215397", 2, true, "example-image-url", 1L, descriptions, images,
				prices);
		return productDto;
	}

	public static ProductDto createDuplicateProductDto(long parentId) {
		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("clothes", 3, true, "example-image-url", parentId, descriptions, images,
				prices);

		return productDto;
	}

	public static ProductDto createDuplicateProductDtoWithChangeInCase(long parentId) {

		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("Clothes", 3, true, "example-image-url", parentId, descriptions, images,
				prices);

		return productDto;
	}

	public static ProductDto createProductDtoWithInvalidEndDate() {

		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", "05/01/2022");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("Clothes56465", 3, true, "example-image-url", 1L, descriptions, images,
				prices);

		return productDto;
	}

	public static ProductDto createProductDtoWithInvalidDateFormat() {

		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(22.0, new Date().toString(), "15/11/2025");

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("Invalid Date Format", 3, true, "Invalid Date Format", 1L, descriptions,
				images, prices);

		return productDto;
	}

	public static ProductDto createProductDtoWithNoEndDate(long parentId) {

		List<ProductDescriptionDto> descriptions = new ArrayList<>();
		descriptions.add(createDescription("English Title", "English Description", "en"));

		List<ProductImageDto> images = new ArrayList<>();
		images.add(createImage("image1", 1));

		DiscountDto discount = createDiscount(35.0, "31/12/2024", null);

		List<PriceDto> prices = new ArrayList<>();
		prices.add(createPrice(50.5, "PS", "ILS", discount));

		ProductDto productDto = createProduct("Clothesfdsg", 3, true, "example-image-url", parentId, descriptions,
				images, prices);

		return productDto;
	}

	private static ProductDescriptionDto createDescription(String title, String description, String language) {
		ProductDescriptionDto descriptionDto = new ProductDescriptionDto();
		descriptionDto.setTitle(title);
		descriptionDto.setDescription(description);
		descriptionDto.setLanguage(language);
		return descriptionDto;
	}

	private static ProductImageDto createImage(String path, int sortOrder) {
		ProductImageDto image = new ProductImageDto();
		image.setPath(path + ".jpg");
		image.setSortOrder(sortOrder);
		return image;
	}

	private static ProductDto createProduct(String code, Integer sortOrder, Boolean active, String image, Long parentId,
			List<ProductDescriptionDto> descriptions, List<ProductImageDto> images, List<PriceDto> prices) {
		ProductDto productDto = new ProductDto();
		productDto.setCode(code);
		productDto.setActive(active);
		productDto.setMainImage(image);
		productDto.setSortOrder(sortOrder);
		productDto.setParentId(parentId);
		productDto.setDescriptions(descriptions);
		productDto.setSubImages(images);
		productDto.setPrices(prices);
		return productDto;
	}

	private static PriceDto createPrice(Double price, String country, String currency, DiscountDto discount) {
		PriceDto priceDto = new PriceDto();
		priceDto.setPrice(price);
		priceDto.setCountryCode(country);
		priceDto.setCurrencyCode(currency);
		priceDto.setDiscount(discount);
		return priceDto;
	}

	private static DiscountDto createDiscount(Double value, String startDate, String endDate) {
		DiscountDto discount = new DiscountDto();
		discount.setValue(value);
		discount.setStartDate(startDate);
		discount.setEndDate(endDate);
		return discount;
	}

	public static long totalNumberCalculator(List<ProductDto> products, String language, Long parentId,
			String searchTerm, Boolean active, Boolean discounted, String country) {

		long result = 0;
		for (ProductDto product : products) {
			boolean activeMatches = active == null || product.isActive() == active;
			boolean searchTermMatches = false;
			boolean parentMatches = parentId == null || parentId.equals(product.getParentId());
			boolean languageMatches = language == null ? true : false;
			boolean discountedMatches = discounted == null || hasDiscount(product, discounted);
			boolean countryMatches = country == null
					|| country.equalsIgnoreCase(product.getPrices().get(0).getCountryCode())
					|| product.getPrices().size() >= 2
							&& country.equalsIgnoreCase(product.getPrices().get(1).getCountryCode())
					|| product.getPrices().size() == 3
							&& country.equalsIgnoreCase(product.getPrices().get(2).getCountryCode());
			if (activeMatches && parentMatches) {
				if (searchTerm != null && !searchTerm.isEmpty()) {
					if (product.getCode() != null
							&& product.getCode().toLowerCase().contains(searchTerm.toLowerCase())) {
						searchTermMatches = true;
					} else if (!product.getDescriptions().isEmpty()) {
						for (ProductDescriptionDto description : product.getDescriptions()) {
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
				for (ProductDescriptionDto description : product.getDescriptions()) {
					if (language.equals(description.getLanguage())) {
						languageMatches = true;
						break;
					}
				}
			}
			if (activeMatches && searchTermMatches && parentMatches && languageMatches && discountedMatches
					&& countryMatches) {
				++result;
			}
		}
		return result;
	}

	private static boolean hasDiscount(ProductDto product, Boolean discounted) {
		if (discounted == null) {
			return true;
		}

		for (PriceDto price : product.getPrices()) {
			if (price.getDiscount() != null && discounted || price.getDiscount() == null && !discounted) {
				return true;
			}
		}
		return false;
	}

	public static Comparator<ProductDto> getCountryAndPriceComparator(String sort) {
		String[] sortTokens = sort.split(",");
		String countryCodeSort = sortTokens[0].trim();
		Comparator<ProductDto> comparator = Comparator.comparing((ProductDto dto) -> {
			// Find the appropriate price based on the given country code
			double price = dto.getPrices().stream()
					.filter(priceObj -> countryCodeSort.equalsIgnoreCase(priceObj.getCountryCode()))
					.mapToDouble(PriceDto::getPrice).findFirst().orElse(Double.MAX_VALUE);
			return price;
		});

		comparator = comparator.thenComparing((ProductDto dto) -> {
			double price = dto.getPrices().stream()
					.filter(priceObj -> countryCodeSort.equalsIgnoreCase(priceObj.getCountryCode()))
					.mapToDouble(PriceDto::getPrice).findFirst().orElse(Double.MAX_VALUE);
			return price;
		}, Comparator.reverseOrder());

		return comparator;
	}

	public static ProductDto updateValidProductDto(ProductDto productDto, String code, String title) {
		productDto.setCode(code);

		// Add a description
		ProductDescriptionDto descriptionDto = null;
		if (productDto.getDescriptions().size() == 0) {
			descriptionDto = new ProductDescriptionDto();
			productDto.getDescriptions().add(descriptionDto);
		} else {
			descriptionDto = productDto.getDescriptions().get(0);
		}
		descriptionDto.setTitle(title);
		descriptionDto.setDescription("English Description");
		descriptionDto.setLanguage("en");

		ProductImageDto image = new ProductImageDto();
		image.setPath(new Date().toString() + "path" + ".jpg");
		productDto.getSubImages().add(image);
		productDto.getSubImages().get(1).setPath("some new path.jpg");
		productDto.getSubImages().remove(0);

		PriceDto price = new PriceDto();
		price.setCountryCode("ps");
		price.setCurrencyCode("ils");
		price.setPrice(Math.random() * 200);
		productDto.getPrices().add(price);
		productDto.getPrices().get(0).setPrice(13.5);
		productDto.getPrices().remove(1);

		return productDto;
	}

	public static ProductDto updateValidCategoryDtoTwoDescriptions(ProductDto productDto, String code, String title) {
		final String[] languages = { "en", "ar" };

		// Add a description
		for (int i = 0; i < languages.length; ++i) {
			boolean found = false;
			for (int j = 0; j < productDto.getDescriptions().size(); ++j) {
				ProductDescriptionDto descriptionDtoToCheck = productDto.getDescriptions().get(j);
				if (descriptionDtoToCheck.getLanguage().equals(languages[i])) {
					descriptionDtoToCheck.setTitle(title + " 1 " + languages[i]);
					descriptionDtoToCheck.setDescription("This is the " + languages[i] + " of " + title);
					found = true;
				}
				if (!found) {
					ProductDescriptionDto descriptionDto = new ProductDescriptionDto();
					descriptionDto.setTitle(title + " 1 " + languages[i]);
					descriptionDto.setDescription("This is the " + languages[i] + " of " + title);
					descriptionDto.setLanguage("en");
					productDto.getDescriptions().add(descriptionDto);
				}
			}
		}

		ProductImageDto image = new ProductImageDto();
		image.setPath(new Date().toString() + ".jpg");
		productDto.getSubImages().add(image);

		PriceDto price = new PriceDto();
		price.setCountryCode("ps");
		price.setCurrencyCode("ils");
		price.setPrice(Math.random() * 200);
		productDto.getPrices().add(price);

		return productDto;
	}

}
