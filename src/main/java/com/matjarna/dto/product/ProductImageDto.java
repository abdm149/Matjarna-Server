package com.matjarna.dto.product;

import java.util.Objects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductImageDto {

	private Long id;

	@NotBlank(message = "Invalid empty path")
	@NotNull(message = "Invalid null path")
	private String path;

	@Min(value = 0, message = "Sort order must be positive")
	private int sortOrder;

	public ProductImageDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductImageDto productImage = (ProductImageDto) o;
		return productImage.getPath().equals(this.path) && productImage.getSortOrder() == sortOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, sortOrder);
	}
}
