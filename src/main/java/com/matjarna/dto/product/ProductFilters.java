package com.matjarna.dto.product;

import com.matjarna.dto.SearchFilters;

public class ProductFilters extends SearchFilters {

	private Long parentId;

	private Boolean discounted;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean isDiscounted() {
		return discounted;
	}

	public void setDiscounted(Boolean discounted) {
		this.discounted = discounted;
	}

}
