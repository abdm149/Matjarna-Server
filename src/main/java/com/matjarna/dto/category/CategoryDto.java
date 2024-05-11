package com.matjarna.dto.category;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CategoryDto {

	private Long id;

	@NotBlank(message = "Invalid blank code")
	private String code;

	@Min(value = 0, message = "Sort order must be positive")
	private int sortOrder;

	@NotNull(message = "Invalid null active")
	private Boolean active;

	@NotNull(message = "Invalid null image")
	private String image;

	@Min(value = 1, message = "ID should be at least 1")
	private Long parentId;

	private long numberOfChildren = 0;

	@NotNull(message = "Invalid null descriptions")
	@NotEmpty(message = "Invalid empty descriptions")
	private List<@Valid CategoryDescriptionDto> descriptions;

	private long numberOfProducts = 0;

	public CategoryDto() {
	}

	public CategoryDto(CategoryDto categoryDto) {
		this.id = categoryDto.id;
		this.code = new String(categoryDto.getCode());
		this.active = categoryDto.isActive();
		this.image = new String(categoryDto.getImage());
		this.numberOfProducts = categoryDto.getNumberOfProducts();
		this.parentId = categoryDto.getParentId();
		this.sortOrder = categoryDto.getSortOrder();
		List<CategoryDescriptionDto> descriptions = new ArrayList<>();
		for (CategoryDescriptionDto description : categoryDto.getDescriptions()) {
			descriptions.add(new CategoryDescriptionDto(description));
		}
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public long getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(long numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public List<CategoryDescriptionDto> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<CategoryDescriptionDto> descriptions) {
		this.descriptions = descriptions;
	}

	public long getNumberOfProducts() {
		return numberOfProducts;
	}

	public void setNumberOfProducts(long numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	@Override
	public String toString() {
		return "CategoryDto [id=" + id + ", code=" + code + ", sortOrder=" + sortOrder + ", active=" + active
				+ ", descriptions=" + descriptions + "]";
	}

}
