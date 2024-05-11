package com.matjarna.dto.product;

import java.util.ArrayList;
import java.util.List;

import com.matjarna.dto.price.PriceDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

	private Long id;

	@NotBlank(message = "Invalid blank code")
	private String code;

	@Min(value = 0, message = "Sort order must be positive")
	private int sortOrder;

	@NotNull(message = "Invalid null active")
	private Boolean active;

	@NotBlank(message = "Invalid blank image")
	private String mainImage;

	@NotNull
	@Min(value = 1, message = "ID should be at least 1")
	private Long parentId;

	@NotNull(message = "Invalid null descriptions")
	@NotEmpty(message = "Invalid empty descriptions")
	private List<@Valid ProductDescriptionDto> descriptions;

	@NotNull(message = "Invalid null sub images")
	@NotEmpty(message = "Invalid empty images")
	private List<@Valid ProductImageDto> subImages;

	@NotNull(message = "Invalid null prices")
	@NotEmpty(message = "Invalid empty prices")
	private List<@Valid PriceDto> prices = new ArrayList<>();

	public ProductDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<ProductDescriptionDto> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<ProductDescriptionDto> descriptions) {
		this.descriptions = descriptions;
	}

	public List<ProductImageDto> getSubImages() {
		return subImages;
	}

	public void setSubImages(List<ProductImageDto> subImages) {
		this.subImages = subImages;
	}

	public List<PriceDto> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceDto> prices) {
		this.prices = prices;
	}

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", code=" + code + ", sortOrder=" + sortOrder + ", active=" + active + "]";
	}

}
