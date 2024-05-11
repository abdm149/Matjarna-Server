package com.matjarna.dto.category;

import com.matjarna.dto.SearchFilters;

public class CategoryFilters extends SearchFilters {

	private Long parentId;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
