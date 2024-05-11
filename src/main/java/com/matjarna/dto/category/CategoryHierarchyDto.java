package com.matjarna.dto.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryHierarchyDto {

	private Long id;

	private String code;

	private Long parentId;

	private int sortOrder;

	private List<CategoryHierarchyDto> children = new ArrayList<>();

	public CategoryHierarchyDto() {

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<CategoryHierarchyDto> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryHierarchyDto> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "CategoryHierarchyDto [id=" + id + ", code=" + code + "]";
	}

}
